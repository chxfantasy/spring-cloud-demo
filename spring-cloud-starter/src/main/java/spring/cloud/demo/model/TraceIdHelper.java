package spring.cloud.demo.model;

import com.google.common.base.Strings;

/**
 * Created by Harry
 */

/**
 * create traceId for every Thread
 */
public class TraceIdHelper {

    private static ThreadLocal<String> traceIdHolder = new ThreadLocal<String>();
    private static ThreadLocal<String> remoteIpHolder = new ThreadLocal<String>();

    public static void setTraceId( String traceId ) {
        if ( Strings.isNullOrEmpty(traceId) ) return;
        traceIdHolder.set(traceId);
    }
    
    public static String getTraceId() {
		return traceIdHolder.get();
	}
    
    public static void clear() {
		traceIdHolder.remove();
	}
    
    public static void setRemoteIp( String remoteIp ) {
        if ( Strings.isNullOrEmpty(remoteIp) ) return;
        remoteIpHolder.set(remoteIp);
    }
    
    public static String getRemoteIp() {
		return remoteIpHolder.get();
	}
    
    public static void clearRemoteIp() {
    	remoteIpHolder.remove();
	}

}
