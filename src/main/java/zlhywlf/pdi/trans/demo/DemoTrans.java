package zlhywlf.pdi.trans.demo;

import org.pentaho.di.core.parameters.DuplicateParamException;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.BasePdiTrans;
import zlhywlf.pdi.core.IPdiStep;


/**
 * @author zlhywlf
 */
@Component
public class DemoTrans extends BasePdiTrans<DemoTrans.IDemoStep> {

    static final String ADD_COUNTER_FIELD = "addCounterField";
    static final String DUMMY = "dummy";
    static final String GENERATE_SOME_ROWS = "generateSomeRows";

    static final String VAR_ROW = "ROW";

    @Override
    public String des() {
        return "DemoTrans";
    }

    @Override
    public void definitionParams(TransMeta transMeta) {
        try {
            transMeta.addParameterDefinition(VAR_ROW, "11", "ParameterDefinition");
        } catch (
                DuplicateParamException e) {
            e.printStackTrace();
        }
    }

    interface IDemoStep extends IPdiStep {
    }
}
