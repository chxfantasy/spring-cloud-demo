package spring.cloud.biz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.cloud.biz.config.datasourceConfig.DataSourceType;
import spring.cloud.biz.config.datasourceConfig.TargetDataSource;
import spring.cloud.biz.service.CommentService;
import spring.cloud.client.model.CommentModel;
import spring.cloud.demo.model.ListResultModel;
import spring.cloud.demo.model.ResultModel;

/**
 * Created by Harry on 15/12/2017.
 */
@RestController
@RequestMapping("/comment")
@TargetDataSource(DataSourceType.COMMENT)        //using the moment DataSource
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger( CommentController.class );

    @Autowired private CommentService commentService;

    @GetMapping("/{momentId}/list")
    public ListResultModel<CommentModel> listCommentsByMomentId( @PathVariable Long momentId ) {
        return this.commentService.listCommentsByMomentId( momentId );
    }

    @PostMapping("/{momentId}/add")
    public ResultModel<CommentModel> addComment(
            @PathVariable Long momentId,
            @RequestParam String content) {
        return this.commentService.addComment( momentId, content );
    }

}
