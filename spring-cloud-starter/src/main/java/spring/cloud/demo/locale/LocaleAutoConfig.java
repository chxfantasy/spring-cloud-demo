package spring.cloud.demo.locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import spring.cloud.demo.model.ErrorCodeMap;

/**
 * Created by Harry on 2017/5/27.
 */
@Configuration
public class LocaleAutoConfig {

    @Bean
    @ConditionalOnMissingBean(LocaleResolver.class)
    public LocaleResolver localeResolver() {
        RequestLocaleResolver rlr = new RequestLocaleResolver();
        return rlr;
    }

    @Bean
    @ConditionalOnMissingBean(LocaleChangeInterceptor.class)
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    @ConditionalOnMissingBean(LocaleMessageService.class)
    public LocaleMessageService localeMessageService(@Autowired MessageSource messageSource){
        LocaleMessageService localeMessageService = new LocaleMessageService(messageSource);
        ErrorCodeMap.init(localeMessageService);
        return localeMessageService;
    }

}