package spring.cloud.gateway.feignService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import spring.cloud.client.model.MomentModel;
import spring.cloud.demo.model.ListResultModel;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.gateway.config.GlobalConstants;

/**
 * Created by Harry on 15/12/2017.
 */

@FeignClient(name = GlobalConstants.BIZ_SERVICE_NAME, path = "/moment")
public interface MomentFeignService {

    @GetMapping("/list")
    ListResultModel<MomentModel> listFirstPageMoment(
            @RequestParam("page") Integer page,
            @RequestParam("pageSize") Integer pageSize
    );

    @PostMapping("")
    ResultModel<MomentModel> addMoment(
            @RequestParam("userId") String userId,
            @RequestParam("content") String content
    );

}
