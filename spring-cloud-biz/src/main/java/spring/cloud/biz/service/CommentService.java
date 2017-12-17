package spring.cloud.biz.service;

import spring.cloud.client.model.CommentModel;
import spring.cloud.demo.model.ListResultModel;
import spring.cloud.demo.model.ResultModel;

/**
 * Created by Harry on 16/12/2017.
 */
public interface CommentService {
    ListResultModel<CommentModel> listCommentsByMomentId(Long momentId);

    ResultModel<CommentModel> addComment(Long momentId, String content);
}
