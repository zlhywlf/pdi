package zlhywlf.pdi.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.parameters.UnknownParamException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * @author zlhywlf
 */
@Slf4j
public abstract class BasePdiTrans<T extends IPdiStep> implements IPdiTrans {
    @Resource
    private Map<String, T> stepConfigMap;
    @Setter
    private TransMeta transMeta = new TransMeta();

    @PostConstruct
    public void configureForTransMeta() {
        Set<Map.Entry<String, T>> entries = stepConfigMap.entrySet();
        for (Map.Entry<String, T> entry : entries) {
            IPdiStep value = entry.getValue();
            transMeta.addStep(value.getStep());
            String preStepConfigName = value.getPreName();
            if (preStepConfigName != null && stepConfigMap.containsKey(preStepConfigName)) {
                transMeta.addTransHop(new TransHopMeta(stepConfigMap.get(preStepConfigName).getStep(), value.getStep()));
            }
        }
        transMeta.setName(this.getClass().getSimpleName());
        definitionMeta(transMeta);
    }

    @Override
    public String execute(Map<String, String> params, Function<List<Map<String, Object>>, String> fun) {
        setParameter(transMeta, params);
        Trans trans = new Trans(transMeta);
        trans.setLogLevel(LogLevel.BASIC);
        try {
            trans.execute(new String[0]);
        } catch (KettleException e) {
            e.printStackTrace();
        }
        trans.waitUntilFinished();
        List<RowMetaAndData> resultRows = trans.getResultRows();
        // TODO 需要修改
        List<Map<String, Object>> dataArr = new ArrayList<>();
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
            dataArr.add(jo);
        }
        return fun.apply(dataArr);
    }


    private void setParameter(TransMeta meta, Map<String, String> paramMap) {
        String[] declaredParameters = meta.listParameters();
        if (declaredParameters.length > 0) {
            try {
                for (String parameterName : declaredParameters) {
                    String description = meta.getParameterDescription(parameterName);
                    String defaultValue = meta.getParameterDefault(parameterName);
                    String parameterValue = defaultValue;
                    if (paramMap.containsKey(parameterName)) {
                        parameterValue = paramMap.get(parameterName);
                        meta.setParameterValue(parameterName, parameterValue);
                        log.info("Setting parameter [{}] to \"{}\" [description: \"{}\", default: \"{}\"]", parameterName, parameterValue, description, defaultValue);
                    } else {
                        log.warn("parameter [{}] is undefined", parameterName);
                    }
                }
            } catch (UnknownParamException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void definitionMeta(TransMeta transMeta);
}
