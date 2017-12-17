package spring.cloud.account.config.interceptors;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;

@Configuration
public class InteceptorAdder extends WebMvcConfigurerAdapter {

    @Autowired private StringHttpMessageConverter stringHttpMessageConverter;
    @Autowired private FastJsonHttpMessageConverter4 fastConverter;
    @Autowired private ByteArrayHttpMessageConverter byteArrayHttpMessageConverter;

	@Autowired private GlobalAspectInteceptor globalAspectInteceptor;
	@Autowired private LocaleChangeInterceptor localeChangeInterceptor;

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{
			"classpath:/META-INF/resources/",
			"classpath:/WEB-INF/resources/",
			"classpath:/resources/",
			"classpath:/static/",
			"classpath:/public/"
	};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		/*
		 /**表示拦截/下的所有路径， /*表示只拦截/下的一级路径  
		 */
		registry.addInterceptor(globalAspectInteceptor).addPathPatterns("/**");
		registry.addInterceptor( localeChangeInterceptor );
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).
				favorParameter(false).
				ignoreAcceptHeader(false).
				useJaf(false).
				defaultContentType(MediaType.TEXT_HTML).
				mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
		converters.add( this.byteArrayHttpMessageConverter );
		converters.add( this.stringHttpMessageConverter );
		converters.add( this.fastConverter );
	}
}