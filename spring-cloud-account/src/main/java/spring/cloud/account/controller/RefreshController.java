package spring.cloud.account.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.cloud.client.uitils.HttpClientUtil;

import java.util.Optional;

/**
 * Created by wangxiaohu on 2017/8/3.
 */
@RestController
@Api(description = "动态刷新配置文件相关接口")
public class RefreshController {

    @Value("${management.port}")
    private int managementPort;

    @ApiOperation(value = "刷新配置文件", notes = "将端口转到managementPort")
    @RequestMapping("/refresh")
    public String refresh(){
        String url = "http://127.0.0.1:"+managementPort+"/refresh";
        String result = null;
        Optional<String> resultOp = HttpClientUtil.post(url);
        if (resultOp.isPresent()){
            result = resultOp.get();
        }
        return result;
    }
}
