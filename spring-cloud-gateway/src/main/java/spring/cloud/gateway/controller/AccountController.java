package spring.cloud.gateway.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.cloud.client.model.AccountModel;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.gateway.service.AccountService;

import javax.servlet.http.HttpServletResponse;

@Controller
@Api(description = "用户信息相关接口")
public class AccountController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired private AccountService accountService;

	@ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息，userId\n\r\t" +
			"不传userId的话，默认取当前用户的信息")
	@GetMapping(path = "/account/detail")
	@ResponseBody
	public ResultModel<AccountModel> detail(@RequestParam(value = "userId", required = false) String userId) {
		return this.accountService.detailByUserId(userId);
	}

	@GetMapping("/index")
	public String index(){
		return "login";
	}

	@PostMapping("/account/login")
	@ResponseBody
	public ResultModel<String> login(
			HttpServletResponse response,
			@RequestParam String userId,
			@RequestParam String password ){
		return this.accountService.login(response, userId, password);
	}
}