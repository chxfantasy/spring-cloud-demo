package spring.cloud.gateway.service;

import spring.cloud.client.model.AccountModel;
import spring.cloud.demo.model.ResultModel;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Harry on 14/12/2017.
 */
public interface AccountService {
    ResultModel<AccountModel> detailByUserId(String userId);

    ResultModel<String> login(HttpServletResponse response, String userId, String password);
}
