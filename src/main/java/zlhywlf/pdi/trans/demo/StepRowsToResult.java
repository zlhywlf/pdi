package zlhywlf.pdi.trans.demo;

import lombok.NoArgsConstructor;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.rowstoresult.RowsToResultMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.BasePdiStep;

/**
 * @author zlhywlf
 */
@NoArgsConstructor
@Component(DemoTrans.DUMMY)
public class StepRowsToResult extends BasePdiStep implements DemoTrans.IDemoStep {

    @Override
    protected StepMetaInterface configureForMeta() {
        return new RowsToResultMeta();
    }


    @Override
    protected Point configureForPoint() {
        return new Point(500, 100);
    }

    @Override
    public String getPreName() {
        return DemoTrans.ADD_COUNTER_FIELD;
    }
}
