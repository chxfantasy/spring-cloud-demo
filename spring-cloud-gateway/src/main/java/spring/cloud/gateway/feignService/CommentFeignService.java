package spring.cloud.gateway.feignService;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;
import spring.cloud.client.model.CommentModel;
import spring.cloud.demo.model.ListResultModel;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.gateway.config.GlobalConstants;

/**
 * Created by Harry on 15/12/2017.
 */
@FeignClient(name = GlobalConstants.BIZ_SERVICE_NAME, path = "/comment")
public interface CommentFeignService {

    @GetMapping("/{momentId}/list")
    ListResultModel<CommentModel> listCommentsByMomentId( @PathVariable("momentId") Long momentId );

    @PostMapping("/{momentId}/add")
    ResultModel<CommentModel> addComment(
            @PathVariable("momentId") Long momentId,
            @RequestParam("content") String content);

}
