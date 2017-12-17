package spring.cloud.demo;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import spring.cloud.demo.springCloud.CustomHystrixConcurrencyStrategy;
import spring.cloud.demo.springCloud.TraceIdFeignHttpRequrestInterceptor;
import spring.cloud.demo.springCloud.TraceIdHttpRequestInterceptor;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.netflix.hystrix.strategy.HystrixPlugins;

import feign.Request;
import feign.RequestInterceptor;

/**
 * Created by Harry on 2017/6/27.
 */
@Configuration
public class GlobalBean implements CommandLineRunner{

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return stringConverter;
    }

    @Bean
    public Decoder customDecoder(
            @Autowired StringHttpMessageConverter stringHttpMessageConverter,
            @Autowired FastJsonHttpMessageConverter4 fastConverter
    ) {

        HttpMessageConverters decodeConverters = new HttpMessageConverters(false,
                Arrays.asList(stringHttpMessageConverter, fastConverter));
        ObjectFactory<HttpMessageConverters> objectFactory = () -> decodeConverters;
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public FastJsonHttpMessageConverter4 fastConverter() {
        FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures( SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect );
        //SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
//        		SerializerFeature.WriteNullBooleanAsFalse,
//        		SerializerFeature.WriteNullListAsEmpty,
//        		SerializerFeature.WriteNullNumberAsZero,
//        		SerializerFeature.WriteNullStringAsEmpty,
//        		SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //默认就是UTF-8
        fastConverter.setDefaultCharset(Charset.forName("UTF-8"));

        return fastConverter;
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter(){
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        return byteArrayHttpMessageConverter;
    }

    //For feignClient
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new TraceIdFeignHttpRequrestInterceptor();
    }

    @Bean
    public TraceIdHttpRequestInterceptor traceIdHttpRequestInterceptor() {
        return new TraceIdHttpRequestInterceptor();
    }
    
    @Bean
    @ConditionalOnProperty(value={"spring.fegin.request.connectTimeoutMillis","spring.fegin.request.readTimeoutMillis"})
    @ConfigurationProperties(prefix = "spring.fegin.request")
    public Request.Options feignRequestOptions(){
        return new Request.Options();
    }
    
    @Bean
    @ConditionalOnProperty(
    		value={"spring.rest.connection.connection-request-timeout",
    				"spring.rest.connection.connect-timeout",
    			"spring.rest.connection.read-timeout"}
    		)
    @ConfigurationProperties(prefix = "spring.rest.connection")
    public HttpComponentsClientHttpRequestFactory customHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(@Autowired(required=false) HttpComponentsClientHttpRequestFactory customHttpRequestFactory,
                                     @Autowired(required=false) FastJsonHttpMessageConverter4 fastConverter,
                                     @Autowired(required=false) TraceIdHttpRequestInterceptor traceIdHttpRequestInterceptor ){

        RestTemplate restTemplate ;
        if ( null == customHttpRequestFactory ) {
        	restTemplate = new RestTemplate();			
		}
        else {
        	restTemplate = new RestTemplate(customHttpRequestFactory);			
		}
        if ( null != fastConverter ) {
        	restTemplate.getMessageConverters().add(fastConverter);			
		}
        if ( null != traceIdHttpRequestInterceptor ) {
        	restTemplate.getInterceptors().add(traceIdHttpRequestInterceptor);			
		}

        return restTemplate;
    }
    
    @Override
	public void run(String... arg0) throws Exception {
		HystrixPlugins.getInstance().registerConcurrencyStrategy(new CustomHystrixConcurrencyStrategy());
	}
}
