package zlhywlf.pdi.core;

import lombok.Getter;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;


/**
 * @author zlhywlf
 */
public abstract class AbstractPdiStep<T extends StepMetaInterface> {

    @Getter
    private final StepMeta step;

    static {

        System.setProperty("KETTLE_PLUGIN_CLASSES", "org.pentaho.di.trans.steps.addsequence.AddSequenceMeta");
        try {
            KettleEnvironment.init(false);
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }

    public AbstractPdiStep() {
        T meta = configureForMeta();
        step = new StepMeta(PluginRegistry.getInstance().getPluginId(StepPluginType.class, meta), this.getClass().getSimpleName(), meta);
        step.setDraw(true);
        step.setLocation(configureForPoint());
    }


    /**
     * step 元数据配置
     *
     * @return step
     */
    protected abstract T configureForMeta();

    /**
     * step 显示位置
     *
     * @return step 显示位置
     */
    protected abstract Point configureForPoint();


}
