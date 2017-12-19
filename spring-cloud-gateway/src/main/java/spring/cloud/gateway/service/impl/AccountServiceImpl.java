package spring.cloud.gateway.service.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.cloud.client.model.AccountModel;
import spring.cloud.client.uitils.ParamCheckUtils;
import spring.cloud.client.uitils.RandomGenerator;
import spring.cloud.demo.cache.CacheService;
import spring.cloud.demo.model.ResultModel;
import spring.cloud.demo.model.TraceIdHelper;
import spring.cloud.gateway.config.AccountHelper;
import spring.cloud.gateway.config.GlobalConstants;
import spring.cloud.gateway.feignService.AccountFeignService;
import spring.cloud.gateway.service.AccountService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Harry on 14/12/2017.
 */

@Service("accountService")
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger( AccountServiceImpl.class );

    @Autowired private AccountFeignService accountFeignService;
    @Autowired private CacheService cacheService;


    @Override
    public ResultModel<AccountModel> detailByUserId(String userId) {
        if (Strings.isNullOrEmpty( userId ) || userId.trim().length() == 0) {
            userId = AccountHelper.getUserId();
        }
        else {
            userId = userId.trim();
        }
        return this.accountFeignService.detailByUserId(userId);
    }

    @Override
    public ResultModel<String> login(HttpServletResponse response, String userId, String password) {
        Optional<String> valOp = ParamCheckUtils.checkParams(Arrays.asList(userId, password), "userId","password");
        if ( valOp.isPresent() ) {
            LOGGER.error("traceId:{}, login, param valid fail, userId:{}, password:{}",
                    TraceIdHelper.getTraceId(), userId, password );
            return ResultModel.createFail("invalidParam");
        }

        ResultModel<String> validateResult = this.accountFeignService.validateUserIdAndPassword(userId, password);
        if ( !ResultModel.CODE_SUCCESS.equals(validateResult.getCode()) ) {
            LOGGER.error("traceId:{}, login fail, errorCode:{}, errorMsg:{}, userId:{}, password:{}",
                    TraceIdHelper.getTraceId(), validateResult.getCode(), validateResult.getMsg() );
            return validateResult;
        }

        //FIXME  not a good way to generate ticket
        String ticket = RandomGenerator.generateNumerString(32);
        try {
            this.cacheService.putString(ticket, userId, GlobalConstants.LOGIN_TOKEN_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
            //put ticket to redis fail
            return ResultModel.createFail("internalErr");
        }

        Cookie cookie = new Cookie(GlobalConstants.LOGIN_TOKEN_KEY, ticket);
        cookie.setMaxAge( GlobalConstants.LOGIN_TOKEN_EXPIRE );
        cookie.setPath("/");
        response.addCookie( cookie );

        return ResultModel.createSuccess();
    }
}
