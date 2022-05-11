package zlhywlf.pdi.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * @author zlhywlf
 */
@Slf4j
public abstract class AbstractPdiTrans<T extends IPdiStep> {
    @Getter
    @Resource
    private Map<String, T> stepConfigMap;
    private final TransMeta transMeta = new TransMeta();

    @PostConstruct
    public void configureForTransMeta() {
        Set<Map.Entry<String, T>> entries = stepConfigMap.entrySet();
        for (Map.Entry<String, T> entry : entries) {
            IPdiStep value = entry.getValue();
            transMeta.addStep(value.getStep());
            String preStepConfigName = value.getPreName();
            if (preStepConfigName != null && stepConfigMap.containsKey(preStepConfigName)) {
                transMeta.addTransHop(new TransHopMeta(stepConfigMap.get(preStepConfigName).getStep(), value.getStep()));
            }
        }
        transMeta.setName(this.getClass().getSimpleName());
        Map<Integer, DatabaseMeta> databases = configureForDatabase();
        if (databases != null) {
            for (Map.Entry<Integer, DatabaseMeta> entry : databases.entrySet()) {
                transMeta.addDatabase(entry.getKey(), entry.getValue());
            }
        }

    }

    abstract protected Map<Integer, DatabaseMeta> configureForDatabase();


    public String execute(BiFunction<TransMeta, Map<String, String>, String> fun, Map<String, String> params) {
        return fun.apply(transMeta, params);
    }


    public String execute(BiFunction<TransMeta, String, String> fun, String params) {
        return fun.apply(transMeta, params);
    }


    public String execute(BiFunction<TransMeta, String, String> fun) {
        return execute(fun, null);
    }
}
