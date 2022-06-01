package zlhywlf.pdi.core;

import lombok.NonNull;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;


/**
 * @author zlhywlf
 */
@Component
public class Initialization implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
            try {
                KettleEnvironment.init(false);
            } catch (KettleException e) {
                e.printStackTrace();
            }
    }
}
