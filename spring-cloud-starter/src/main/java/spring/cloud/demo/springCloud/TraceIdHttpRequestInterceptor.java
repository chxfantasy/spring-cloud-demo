package spring.cloud.demo.springCloud;

import spring.cloud.demo.StarterConstants;
import spring.cloud.demo.model.TraceIdHelper;
import com.google.common.base.Strings;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by Harry on 2017/6/27.
 */
public class TraceIdHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String traceId = TraceIdHelper.getTraceId();
        String remoteIp = TraceIdHelper.getRemoteIp();
        if (Strings.isNullOrEmpty(traceId)) {
            request.getHeaders().add(StarterConstants.TRACE_ID_KEY, traceId);
        }
        if (Strings.isNullOrEmpty(remoteIp)) {
            request.getHeaders().add(StarterConstants.IP_HEADER_NAME, remoteIp);
        }
        return execution.execute(request, body);
    }

}
