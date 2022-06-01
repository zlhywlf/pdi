package zlhywlf.pdi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author zlhywlf
 */
@Slf4j
@Component
public class PdiUtils {
    public static String saveTrans(TransMeta transMeta, String path) throws KettleException, IOException {
        String outputFilename = path;
        if (StringUtils.isEmpty(outputFilename)) {
            outputFilename = "etl/" + transMeta.getName() + ".ktr";
        }
        log.info("- Saving to " + outputFilename);
        String xml = transMeta.getXML();
        File file = new File(outputFilename);
        FileUtils.writeStringToFile(file, xml, "UTF-8");
        return outputFilename;
    }
}
