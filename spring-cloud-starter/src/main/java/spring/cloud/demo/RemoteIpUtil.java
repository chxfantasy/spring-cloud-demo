package spring.cloud.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

public class RemoteIpUtil {
	
	public static String getRemoteIp(HttpServletRequest servletRequest) {
        String ip = null;
        //先获取我们自己的设置的header
        if ( Strings.isNullOrEmpty(ip) ) {
            ip = servletRequest.getHeader( StarterConstants.IP_HEADER_NAME);
        }
        if ( Strings.isNullOrEmpty(ip) ) {
            ip = servletRequest.getHeader("X-Forwarded-For");
        }
        if ( Strings.isNullOrEmpty(ip) ) {
            ip = servletRequest.getHeader("X-Real-IP");
        }
        if ( Strings.isNullOrEmpty(ip) ) {
            ip = servletRequest.getHeader("Remote-IP");
        }
        if ( Strings.isNullOrEmpty(ip) ) {
        	ip = servletRequest.getRemoteHost();
        }
        if ( Strings.isNullOrEmpty(ip) ) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        if ( Strings.isNullOrEmpty(ip) ) {
            ip = "127.0.0.1";
        }

        return ip;
    }
	
}
