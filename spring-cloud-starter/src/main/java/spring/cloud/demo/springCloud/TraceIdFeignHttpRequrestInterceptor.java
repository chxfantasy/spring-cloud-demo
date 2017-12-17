package spring.cloud.demo.springCloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import spring.cloud.demo.StarterConstants;
import spring.cloud.demo.model.TraceIdHelper;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Created by Harry on 2017/6/27.
 */
@Component
public class TraceIdFeignHttpRequrestInterceptor implements RequestInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( TraceIdFeignHttpRequrestInterceptor.class );
	
    @Override
    public void apply(RequestTemplate template) {
    	
    	try {
    		template.header( StarterConstants.TRACE_ID_KEY, TraceIdHelper.getTraceId() );
        	template.header( StarterConstants.IP_HEADER_NAME, TraceIdHelper.getRemoteIp() );
        	
            String bodyStr = "";
            byte[] body = template.body();
            if ( null != body && body.length > 0 ) {
    			bodyStr = new String( body );
    		}
            LOGGER.info( "traceId:{}, request -> path:{}, headers:{}, querys:{}, body:{}",
            		TraceIdHelper.getTraceId(), template.request().url(), JSON.toJSONString(template.request().headers()),
            		JSON.toJSONString(template.queries()), bodyStr );			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
