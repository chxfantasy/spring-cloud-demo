package spring.cloud.eureka.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Harry on 08/09/2017.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "success";
    }

}
