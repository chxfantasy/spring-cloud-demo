package spring.cloud.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.cloud.account.service.AccountService;
import spring.cloud.client.model.AccountModel;
import spring.cloud.demo.model.ResultModel;

@RestController
@RequestMapping("/account")
@Api(description = "用户信息相关接口")
public class AccountController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired private AccountService accountService;

	@ApiOperation(value = "获取用户详细信息")
	@GetMapping("/detail")
	public ResultModel<AccountModel> detailByUserId(@RequestParam("userId") String userId){
		return this.accountService.detail(userId);
	}

	@PostMapping("/validate")
	public ResultModel<String> validateUserIdAndPassword(
			@RequestParam("userId") String userId,
			@RequestParam("password") String password
	){
		return this.accountService.validateUserIdAndPassword(userId, password);
	}

}