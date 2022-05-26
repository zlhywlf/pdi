package zlhywlf.pdi.trans.demo.steps;

import lombok.NoArgsConstructor;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.rowgenerator.RowGeneratorMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.AbstractPdiStep;
import zlhywlf.pdi.trans.demo.DemoTrans;
import zlhywlf.pdi.trans.demo.IDemoStep;

/**
 * @author zlhywlf
 */
@NoArgsConstructor
@Component(DemoTrans.GENERATE_SOME_ROWS)
public class GenerateSomeRows extends AbstractPdiStep implements IDemoStep {
    @Override
    protected StepMetaInterface configureForMeta() {
        RowGeneratorMeta rowGeneratorMeta = new RowGeneratorMeta();
        rowGeneratorMeta.setRowLimit("5");
        rowGeneratorMeta.allocate(2);
        rowGeneratorMeta.setFieldName(new String[]{"field_1", "field_2"});
        rowGeneratorMeta.setFieldType(new String[]{"String", "Integer"});
        rowGeneratorMeta.setValue(new String[]{"Hello World", "100"});
        return rowGeneratorMeta;
    }

    @Override
    protected Point configureForPoint() {
        return new Point(100, 100);
    }

    @Override
    public String getPreName() {
        return null;
    }
}
