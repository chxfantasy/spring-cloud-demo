package spring.cloud.account.config.interceptors;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import spring.cloud.demo.RemoteIpUtil;
import spring.cloud.demo.StarterConstants;
import spring.cloud.demo.model.TraceIdHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class GlobalAspectInteceptor implements HandlerInterceptor{
	private static final Logger LOGGER = LoggerFactory.getLogger( GlobalAspectInteceptor.class );
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//traceId
		String traceIdFromRequest = request.getHeader(StarterConstants.TRACE_ID_KEY);
		if ( Strings.isNullOrEmpty(traceIdFromRequest) ) {
			traceIdFromRequest = UUID.randomUUID().toString().toUpperCase();
			request.setAttribute(StarterConstants.TRACE_ID_KEY,traceIdFromRequest);
		}

		TraceIdHelper.setTraceId( traceIdFromRequest );
		TraceIdHelper.setRemoteIp(RemoteIpUtil.getRemoteIp(request));

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//set global params for web
	    if (null != modelAndView) {
//            modelAndView.addObject("version", "127.0.2");
        }
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		TraceIdHelper.clear();
	}

}
