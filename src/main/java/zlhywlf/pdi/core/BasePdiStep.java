package zlhywlf.pdi.core;

import lombok.Getter;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;


/**
 * @author zlhywlf
 */
public abstract class BasePdiStep {

    @Getter
    private final StepMeta step;

    public BasePdiStep() {
        StepMetaInterface meta = configureForMeta();
        step = new StepMeta(PluginRegistry.getInstance().getPluginId(StepPluginType.class, meta), this.getClass().getSimpleName(), meta);
        step.setDraw(true);
        step.setLocation(configureForPoint());
    }


    /**
     * step 元数据配置
     *
     * @return step
     */
    protected abstract StepMetaInterface configureForMeta();

    /**
     * step 显示位置
     *
     * @return step 显示位置
     */
    protected abstract Point configureForPoint();


}
