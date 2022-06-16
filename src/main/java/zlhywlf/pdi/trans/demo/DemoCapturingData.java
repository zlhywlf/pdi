package zlhywlf.pdi.trans.demo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.RowAdapter;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zlhywlf
 */
@Component
@Getter
@Setter
public class DemoCapturingData extends RowAdapter {
    public List<Object[]> capturedRows;
    public RowMetaInterface rowStructure;
    private boolean firstRow = true;

    @Override
    public void rowWrittenEvent(RowMetaInterface rowMeta, Object[] row) throws KettleStepException {
        if (firstRow) {
            firstRow = false;
            // a space to keep the captured rows
            capturedRows = new LinkedList<Object[]>();
            // keep the row structure for future reference
            rowStructure = rowMeta;
            // print a header before the first row
            System.out.println(StringUtils.join(rowMeta.getFieldNames(), "\t"));
        }

        try {
            System.out.print(rowMeta.getString(row, 0));
            System.out.print("\t");
            System.out.print(rowMeta.getInteger(row, 1));
            System.out.print("\n");

            // keep the row
            capturedRows.add(row);
        } catch (KettleValueException e) {
            e.printStackTrace();
        }
    }
}
