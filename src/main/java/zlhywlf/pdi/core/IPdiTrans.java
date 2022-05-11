package zlhywlf.pdi.core;

import org.pentaho.di.trans.TransMeta;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author zlhywlf
 */
public interface IPdiTrans {

    String des();


    String execute(BiFunction<TransMeta, Map<String, String>, String> fun, Map<String, String> params);

    String execute(BiFunction<TransMeta, String, String> fun, String path);

    String execute(BiFunction<TransMeta, String, String> fun);

    /**
     * 获取前一个转换的类名
     *
     * @return 类名
     */
    String getPreName();

}
