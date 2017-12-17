package spring.cloud.gateway.config.interceptors;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import spring.cloud.demo.cache.CacheService;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.gateway.config.AccountHelper;
import spring.cloud.gateway.config.GlobalConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Harry on 17/3/23.
 */
@Configuration
@RefreshScope
public class CookieCheckInterceptor implements HandlerInterceptor {

    @Autowired private CacheService cacheService;

    private static final Logger LOGGER = LoggerFactory.getLogger( CookieCheckInterceptor.class );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.info("request -> path:{}, params:{}", request.getRequestURI(), JSON.toJSONString( request.getParameterMap() ) );

        Cookie[] cookies = request.getCookies();
        if ( null == cookies || cookies.length == 0 ) {
            printError( response, JSON.toJSONString(ResultModel.createFail("needLogin")) );
            return false;
        }
        String ticket = null;
        for (Cookie cookie: cookies) {
            if ( null == cookie ) continue;
            String cookieKey = cookie.getName();
            if ( !GlobalConstants.LOGIN_TOKEN_KEY.equals(cookieKey) ) {
                continue;
            }
            ticket = cookie.getValue();
            break;
        }
        if ( Strings.isNullOrEmpty( ticket ) ) {
            printError( response, JSON.toJSONString(ResultModel.createFail("needLogin")) );
            return false;
        }

        String userId = this.cacheService.getString( ticket );
        if ( Strings.isNullOrEmpty( userId ) ) {
            printError( response, JSON.toJSONString(ResultModel.createFail("needLogin")) );
            return false;
        }

        AccountHelper.setUserId( userId );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccountHelper.clear();
    }

    public static void printError(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().print( msg );
        response.getWriter().flush();
        response.getWriter().close();
    }

}
