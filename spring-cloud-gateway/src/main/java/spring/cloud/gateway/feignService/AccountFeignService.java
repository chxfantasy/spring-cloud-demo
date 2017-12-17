package spring.cloud.gateway.feignService;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.cloud.client.model.AccountModel;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.gateway.config.GlobalConstants;

/**
 * Created by Harry on 13/12/2017.
 */

/**
 * called by feignClient, with CircuitBreaker, using eureka, with robin-load-balance
 */
@FeignClient(value = GlobalConstants.ACCOUNT_SERVICE_NAME, path = "/account", decode404 = true)
public interface AccountFeignService {

    @PostMapping("/validate")
    ResultModel<String> validateUserIdAndPassword(
            @RequestParam("userId") String userId,
            @RequestParam("password") String password
    );

    @GetMapping("/detail")
    ResultModel<AccountModel> detailByUserId(@RequestParam("userId") String userId);

}