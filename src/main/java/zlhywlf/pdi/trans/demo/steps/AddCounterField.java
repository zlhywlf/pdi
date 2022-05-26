package zlhywlf.pdi.trans.demo.steps;

import lombok.NoArgsConstructor;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.addsequence.AddSequenceMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.AbstractPdiStep;
import zlhywlf.pdi.trans.demo.DemoTrans;
import zlhywlf.pdi.trans.demo.IDemoStep;

/**
 * @author zlhywlf
 */
@NoArgsConstructor
@Component(DemoTrans.ADD_COUNTER_FIELD)
public class AddCounterField extends AbstractPdiStep implements IDemoStep {

    @Override
    protected StepMetaInterface configureForMeta() {
        AddSequenceMeta addSequenceMeta = new AddSequenceMeta();
        addSequenceMeta.setDefault();
        addSequenceMeta.setValuename( "counter" );
        addSequenceMeta.setCounterName( "counter_1" );
        addSequenceMeta.setStartAt( 1 );
        addSequenceMeta.setMaxValue( Long.MAX_VALUE );
        addSequenceMeta.setIncrementBy( 1 );
        return addSequenceMeta;
    }

    @Override
    protected Point configureForPoint() {
        return new Point(300,100);
    }

    @Override
    public String getPreName() {
        return DemoTrans.GENERATE_SOME_ROWS;
    }

}
