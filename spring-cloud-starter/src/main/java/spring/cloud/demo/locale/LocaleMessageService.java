package spring.cloud.demo.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleMessageService {

	private MessageSource messageSource;
	
	public LocaleMessageService(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	public String getMessage(String code) {
		return getMessage(code, null);
	}
	
	/**
	 *
	 * @param code
	 *            ：对应messages配置的key.
	 * @param args
	 *            : 数组参数.
	 * @return
	 */
	public String getMessage(String code, Object[] args) {
		return getMessage(code, args, "");
	}

	/**
	 *
	 * @param code
	 *            ：对应messages配置的key.
	 * @param args
	 *            : 数组参数.
	 * @param defaultMessage
	 *            : 没有设置key的时候的默认值.
	 * @return
	 */
	public String getMessage(String code, Object[] args, String defaultMessage) {
		// 这里使用比较方便的方法，不依赖request.
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}

}
