package zlhywlf.pdi.trans.demo;

import org.pentaho.di.core.database.DatabaseMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.AbstractPdiTrans;
import zlhywlf.pdi.core.IPdiTrans;

import java.util.Map;


/**
 * @author zlhywlf
 */
@Component
public class DemoTrans extends AbstractPdiTrans<IDemoStep> implements IPdiTrans {

    public static final String ADD_COUNTER_FIELD = "addCounterField";
    public static final String DUMMY = "dummy";
    public static final String GENERATE_SOME_ROWS = "generateSomeRows";

    @Override
    public String des() {
        return "DemoTrans";
    }


    @Override
    public String getPreName() {
        return null;
    }


    @Override
    protected Map<Integer, DatabaseMeta> configureForDatabase() {
        return null;
    }
}
