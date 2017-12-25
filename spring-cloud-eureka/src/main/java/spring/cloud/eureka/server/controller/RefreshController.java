package spring.cloud.eureka.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by wangxiaohu on 2017/7/31.
 */
@RestController
public class RefreshController {

    private static final Logger LOGGER = LoggerFactory.getLogger( RefreshController.class );

    @Autowired private DiscoveryClient discoveryClient;

    @Autowired private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String serverName;

    /**
     * refresh all hosts in all services, not a good idea
     */
    @PostMapping("/allRefresh")
    public void allRefresh() {
        List<String> services = discoveryClient.getServices();
        for (String serviceId : services){
            if (serverName.equals(serviceId)){
                continue;
            }
            List<ServiceInstance> instanceList = discoveryClient.getInstances(serviceId);
            for (ServiceInstance serviceInstance : instanceList) {
                String url = "http://"+ serviceInstance.getHost() +":"+serviceInstance.getPort()+"/refresh";
                String result;
                try {
                    result = this.restTemplate.postForObject(url, null, String.class);
                }catch (Exception e){
                    e.printStackTrace();
                    LOGGER.error("post /refresh Error. url: {}; serviceId: {}, host:{}", url, serviceId, serviceInstance.getHost() );
                    continue;
                }
                LOGGER.info("url: {}; serviceId: {}; changedProfile: {}", url, serviceId, result);
            }
        }
    }
}
