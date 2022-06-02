package zlhywlf.pdi;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.trans.steps.addsequence.AddSequenceMeta;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import zlhywlf.pdi.core.IPdiTrans;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zlhywlf
 */
@Configuration
@ComponentScan("zlhywlf.pdi")
@Slf4j
public class Main {
    @Resource(name = "demoFileTrans")
    IPdiTrans demoTrans;

    public static void main(String[] args) {
        // 输出元数据时需要添加插件信息
        System.setProperty(Const.KETTLE_PLUGIN_CLASSES, AddSequenceMeta.class.getName());
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        Main bean = context.getBean(Main.class);
        log.info(bean.demoTrans.execute(new HashMap<>(2), bean::toJsonString));
        context.registerShutdownHook();
    }

    public String toJsonString(List<Map<String, Object>> dataArr) {
        Map<String, Object> res = new HashMap<>(1);
        res.put("data", dataArr);
        System.out.println(res);
        return new JSONObject(res).toJSONString();
    }

}
