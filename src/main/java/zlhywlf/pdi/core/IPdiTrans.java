package zlhywlf.pdi.core;

import org.pentaho.di.core.RowMetaAndData;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zlhywlf
 */
public interface IPdiTrans {

    String des();


    String execute( Map<String, String> params,Function<List<Map<String, Object>>, String> fun);

}
