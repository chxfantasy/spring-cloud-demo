package spring.cloud.demo.locale;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class RequestLocaleResolver implements LocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String lang = request.getParameter("lang");
		if ( null == lang || lang.trim().length() == 0 ) {
			return Locale.SIMPLIFIED_CHINESE;
		}
		
		return new Locale(lang);
	}
	
	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
//		throw new IllegalStateException("not implemented");
	}

}
