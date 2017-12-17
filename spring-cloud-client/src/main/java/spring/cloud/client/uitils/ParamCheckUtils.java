package spring.cloud.client.uitils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Harry on 17/3/17.
 */
public class ParamCheckUtils {

    public static Optional<String> checkParams(List<String> paramNameList, Object...params) {
        if ( null == paramNameList || paramNameList.isEmpty() ) return Optional.empty();
        if ( null == params ) return Optional.of("the size of nameList and params is not the same");
        if ( paramNameList.size() != params.length ) return Optional.of("the size of nameList and params is not the same");

        for ( int i = 0; i < params.length; i++ ) {
            Object param = params[i];
            if ( null == param ) {
                return Optional.of( paramNameList.get(i) +" is required" );
            }

            if ( param instanceof String ) {
                if ( ((String) param).length() == 0 ) {
                    return Optional.of( paramNameList.get(i) +" is required" );
                }
            }
        }

        return Optional.empty();
    }

}
