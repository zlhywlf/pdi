package zlhywlf.pdi.trans.demo;

import org.pentaho.di.core.exception.KettleMissingPluginsException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.BasePdiTrans;

/**
 * @author zlhywlf
 */
@Component
public class DemoFileTrans extends BasePdiTrans<DemoTrans.IDemoStep> {

    @Override
    public void configureForTransMeta() {
        try {
            setTransMeta(new TransMeta("etl/DemoTrans.ktr"));
        } catch (KettleXMLException | KettleMissingPluginsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void definitionMeta(TransMeta transMeta) {
    }

    @Override
    public String des() {
        return "null";
    }
}
