package spring.cloud.demo.springCloud;

import spring.cloud.demo.model.TraceIdHelper;

import java.util.concurrent.Callable;

/**
 * Created by Harry on 2017/7/14.
 */
public class CustomCallable<T> implements Callable<T> {
    private Callable<T> realCallable;
    private String traceId;
    private String remoteIp;

    public CustomCallable(Callable<T> callable, String traceId, String remoteIp) {
        this.realCallable = callable;
        this.traceId = traceId;
        this.remoteIp = remoteIp;
    }

    @Override
    public T call() throws Exception {
        TraceIdHelper.setTraceId( traceId );
        TraceIdHelper.setRemoteIp(remoteIp);
        return realCallable.call();
    }
}
