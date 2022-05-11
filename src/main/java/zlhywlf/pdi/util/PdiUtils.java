package zlhywlf.pdi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.parameters.UnknownParamException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zlhywlf
 */
@Slf4j
@Component
public class PdiUtils {

    public String runTrans(TransMeta transMeta, Map<String, String> paramMap) {
        setParameter(transMeta, paramMap);
        Trans trans = new Trans(transMeta);
        trans.setLogLevel(LogLevel.BASIC);
        try {
            trans.execute(new String[0]);
        } catch (KettleException e) {
            e.printStackTrace();
        }
        trans.waitUntilFinished();
        printLog(trans.getResult(), transMeta.getName(), "Trans", KettleLogStore.getAppender().getBuffer(trans.getLogChannelId(), false).toString());

        List<RowMetaAndData> resultRows = trans.getResultRows();
        ArrayList<Map<String, Object>> jsonArray = new ArrayList<>();
        for (RowMetaAndData data : resultRows) {
            RowMetaInterface rowMeta = data.getRowMeta();
            List<ValueMetaInterface> valueMetaList = rowMeta.getValueMetaList();
            Map<String, Object> jo = new HashMap<>(3);
            for (ValueMetaInterface v : valueMetaList) {

                try {
                    switch (v.getType()) {
                        case ValueMetaInterface.TYPE_BOOLEAN:
                            jo.put(v.getName(), data.getBoolean(v.getName(), false));
                            break;
                        case ValueMetaInterface.TYPE_INTEGER:

                            jo.put(v.getName(), data.getInteger(v.getName()));
                            break;
                        case ValueMetaInterface.TYPE_NUMBER:

                            jo.put(v.getName(), data.getNumber(v.getName(), 0.0));
                            break;
                        case ValueMetaInterface.TYPE_BIGNUMBER:
                            jo.put(v.getName(), data.getBigNumber(v.getName(), new BigDecimal(0)));
                            break;
                        default:
                            jo.put(v.getName(), data.getString(v.getName(), ""));
                            break;
                    }
                } catch (KettleValueException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.add(jo);
        }
        Map<String, Object> res = new HashMap<>(1);
        res.put("data", jsonArray);
        String s = new JSONObject(res).toJSONString();
        return StringUtils.isNotEmpty(s) ? s : String.valueOf(trans.getResult().getResult());
    }


    public String saveTrans(TransMeta transMeta, String path) throws KettleException, IOException {
        String outputFilename = path;
        if (StringUtils.isEmpty(outputFilename)) {
            outputFilename = "etl/" + transMeta.getName();
        }
        log.info("- Saving to " + outputFilename);
        String xml = transMeta.getXML();
        File file = new File(outputFilename);
        FileUtils.writeStringToFile(file, xml, "UTF-8");
        return outputFilename;
    }

    private void setParameter(TransMeta meta, Map<String, String> paramMap) {
        String[] declaredParameters = meta.listParameters();
        if (declaredParameters.length > 0) {
            String description;
            String defaultValue;
            String parameterValue;
            try {
                for (String parameterName : declaredParameters) {

                    description = meta.getParameterDescription(parameterName);

                    defaultValue = meta.getParameterDefault(parameterName);
                    parameterValue = defaultValue;
                    if (paramMap.containsKey(parameterName)) {
                        parameterValue = paramMap.get(parameterName);
                        meta.setParameterValue(parameterName, parameterValue);
                    }
                    log.info("Setting parameter [{}] to \"{}\" [description: \"{}\", default: \"{}\"]", parameterName, parameterValue, description, defaultValue);
                }

            } catch (UnknownParamException e) {
                e.printStackTrace();
            }
        }
    }

    private void printLog(Result result, String filePath, String type, String logs) {
        log.info("{} {} executed {}", type, filePath, result.getNrErrors() == 0L ? "successfully" : "with " + result.getNrErrors() + " errors");
        String memo = "=====================================================================================";
        log.info("logs\n{}\n{}{}\n", memo, logs, memo);
    }
}
