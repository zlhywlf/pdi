package zlhywlf.pdi.trans.demo;

import lombok.NoArgsConstructor;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.rowgenerator.RowGeneratorMeta;
import org.springframework.stereotype.Component;
import zlhywlf.pdi.core.BasePdiStep;

/**
 * @author zlhywlf
 */
@NoArgsConstructor
@Component(DemoTrans.GENERATE_SOME_ROWS)
public class StepGenerateSomeRows extends BasePdiStep implements DemoTrans.IDemoStep {
    @Override
    protected StepMetaInterface configureForMeta() {
        RowGeneratorMeta rowGeneratorMeta = new RowGeneratorMeta();
        rowGeneratorMeta.setRowLimit("${"+DemoTrans.VAR_ROW+"}");
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
