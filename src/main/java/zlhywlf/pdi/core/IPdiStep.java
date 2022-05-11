package zlhywlf.pdi.core;

import org.pentaho.di.trans.step.StepMeta;

/**
 * @author zlhywlf
 */
public interface IPdiStep {

    StepMeta getStep();

    /**
     * 获取前一个步骤的类名
     *
     * @return 类名
     */
    String getPreName();
}
