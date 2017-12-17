package spring.cloud.demo.Bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangxiaohu on 2017/6/8.
 */
@Configuration
public class SpringBeanAutoConfig {

    @Bean("springUtil")
    public SpringUtil springUtil(){
        return new SpringUtil();
    }
}
