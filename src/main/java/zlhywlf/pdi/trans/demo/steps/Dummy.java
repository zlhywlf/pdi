package zlhywlf.pdi.trans.demo.steps;

import lombok.NoArgsConstructor;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.rowstoresult.RowsToResultMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.AbstractPdiStep;
import zlhywlf.pdi.trans.demo.DemoTrans;
import zlhywlf.pdi.trans.demo.IDemoStep;

/**
 * @author zlhywlf
 */
@NoArgsConstructor
@Component(DemoTrans.DUMMY)
public class Dummy extends AbstractPdiStep implements IDemoStep {

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
