package spring.cloud.biz.service;

import spring.cloud.client.model.MomentModel;
import spring.cloud.demo.model.ListResultModel;
import spring.cloud.demo.model.ResultModel;

/**
 * Created by Harry on 15/12/2017.
 */
public interface MomentService {
    ListResultModel<MomentModel> listFirstPageMoment(Integer page, Integer pageSize);

    ResultModel<MomentModel> addMoment(String userId, String content);
}
