package spring.cloud.account.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import spring.cloud.account.dataaccess.AccountDataAccess;
import spring.cloud.account.dataaccess.dataobject.AccountDo;
import spring.cloud.account.service.AccountService;
import spring.cloud.client.model.AccountModel;
import spring.cloud.client.uitils.CopyProperityUtils;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.demo.model.TraceIdHelper;

import java.util.Optional;

@Service("accountService")
@RefreshScope
public class AccountServiceImpl implements AccountService {

	private final static Logger LOGGER = LoggerFactory.getLogger( AccountServiceImpl.class );

	@Autowired private AccountDataAccess accountDataAccess;
	@Override
	public ResultModel<AccountModel> detail(String userId) {
		if (Strings.isNullOrEmpty( userId ) ) {
			return ResultModel.createFail("invalidParam");
		}
		Optional<AccountDo> accountDoOp = this.accountDataAccess.selectByPrimaryKey(userId);
		if ( !accountDoOp.isPresent() ) {
			LOGGER.error("traceId:{}, AccountService.detail, user does not exist, userId:{}",
					TraceIdHelper.getTraceId(), userId );
			return ResultModel.createFail("noThisUser");
		}
		AccountModel accountModel = new AccountModel();
		CopyProperityUtils.copyAllProperies( accountDoOp.get(), accountModel );

		return ResultModel.createSuccess( accountModel );
	}

	@Override
	public ResultModel<String> validateUserIdAndPassword(String userId, String password) {
		if (Strings.isNullOrEmpty( userId ) || Strings.isNullOrEmpty(password) ) {
			return ResultModel.createFail("invalidParam");
		}
		userId = userId.trim();
		password = password.trim();

		Optional<AccountDo> accountDoOp = this.accountDataAccess.selectByPrimaryKey(userId);
		if ( !accountDoOp.isPresent() ) {
			LOGGER.error("traceId:{}, AccountService.detail, user does not exist, userId:{}",
					TraceIdHelper.getTraceId(), userId );
			//hide the error msg
			return ResultModel.createFail("wrongUserOrPwd");
		}

		AccountDo accountDo = accountDoOp.get();
		if ( password.equals( accountDo.getPassword() ) ) {
			return ResultModel.createSuccess();
		}
		else {
			return ResultModel.createFail("wrongUserOrPwd");
		}
	}
}