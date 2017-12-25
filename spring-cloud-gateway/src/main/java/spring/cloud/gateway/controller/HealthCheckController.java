package spring.cloud.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.cloud.demo.cache.CacheService;
import spring.cloud.demo.model.TraceIdHelper;
import spring.cloud.gateway.feignService.AccountHealthCheckService;
import spring.cloud.gateway.feignService.BizHealthCheckService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Harry on 2017/6/16.
 */
@RestController
@Api(description = "healthCheck")
public class HealthCheckController {

    public static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);

    @Autowired private CacheService cacheService;
    @Autowired private AccountHealthCheckService accountHealthCheckService;
    @Autowired private BizHealthCheckService bizHealthCheckService;

    private static final String HEALTH_KEY = "spring.cloud.gateway#healthCheck#key";

    private static final int CACHE_TIME = 500;  //ms
    private static final int db_TIME = 2000;  //ms

    private static final String FAIL = "fail";
    private static final String SUCCESS = "success";

    @ApiOperation("health check")
    @RequestMapping(value = {"/health","/"}, method = RequestMethod.GET)
    public String health(HttpServletResponse response) {
        /*验证cache*/
        long start = System.currentTimeMillis();
        try {
            this.cacheService.putString(HEALTH_KEY, HEALTH_KEY);
            String strInCache = this.cacheService.getString(HEALTH_KEY);
            if ( !HEALTH_KEY.equals(strInCache) ) {
                LOGGER.error("traceId:{}, healthCheckController error， get from cache:{}, put into cache:{}, not the same", TraceIdHelper.getTraceId(), strInCache, HEALTH_KEY);
                response.setStatus(500);
                return FAIL;
            }
            long end = System.currentTimeMillis();
            if (end - start > 500) {
                LOGGER.error("traceId:{}, healthCheckController error, check cache time expire {}ms, actually:{}ms", TraceIdHelper.getTraceId(),CACHE_TIME, (end-start));
                response.setStatus(500);
                return FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }

        return SUCCESS;
    }

    @GetMapping("/traceHealth")
    public String traceHealth(HttpServletResponse response) {
        String myHealth = this.health(response);
        if ( FAIL.equalsIgnoreCase(myHealth) ) {
            response.setStatus(500);
            return FAIL;
        }

        String imHealth = this.accountHealthCheckService.health();
        if ( FAIL.equalsIgnoreCase( imHealth ) ) {
            LOGGER.error("accountHealthCheckService.health fail");
            response.setStatus(500);
            return FAIL;
        }

        String momentHealth = this.bizHealthCheckService.health();
        if ( FAIL.equalsIgnoreCase(  momentHealth ) ) {
            LOGGER.error("bizHealthCheckService.health fail");
            response.setStatus(500);
            return FAIL;
        }

        return SUCCESS;
    }
}
