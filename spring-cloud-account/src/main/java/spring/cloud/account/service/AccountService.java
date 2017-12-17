package spring.cloud.account.service;

import spring.cloud.client.model.AccountModel;
import spring.cloud.demo.model.ResultModel;

public interface AccountService {

	ResultModel<AccountModel> detail(String userId);

	ResultModel<String> validateUserIdAndPassword(String userId, String password);
}
