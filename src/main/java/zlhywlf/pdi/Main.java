package zlhywlf.pdi;

import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import zlhywlf.pdi.core.IPdiTrans;
import zlhywlf.pdi.util.PdiUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author zlhywlf
 */
@Configuration
@ComponentScan("zlhywlf.pdi")
@Slf4j
public class Main {
    @Resource
    IPdiTrans demoTrans;
    @Resource
    PdiUtils pdiUtils;

    public static void main(String[] args) throws KettleException, IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Main bean = context.getBean(Main.class);
        log.info(bean.demoTrans.execute(bean.pdiUtils::runTrans, new HashMap<>(2)));
        context.registerShutdownHook();
    }


}
