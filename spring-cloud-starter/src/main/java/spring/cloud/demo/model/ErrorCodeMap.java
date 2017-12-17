package spring.cloud.demo.model;

import spring.cloud.demo.locale.LocaleMessageService;
import com.google.common.base.Strings;

/**
 * Created by Harry on 2017/5/19.
 */
public class ErrorCodeMap{
    private static LocaleMessageService localeMessageService;

    public static void init(LocaleMessageService localeMessageService){
        ErrorCodeMap.localeMessageService = localeMessageService;
    }

    public static String getValueByCode(String code) {
        if ( null == code || code.trim().length()==0 ) return "";
        String value = localeMessageService.getMessage(code);
        if ( Strings.isNullOrEmpty(value) ) {
			return code;
		}
        return value;
    }
}