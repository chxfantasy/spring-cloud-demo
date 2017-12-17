package spring.cloud.gateway.config;

import spring.cloud.gateway.config.interceptors.GlobalAspectInteceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableSwagger2
@RefreshScope
public class GlobalBeanConfig {

    @Bean(name = "loggerInteceptor")
    public GlobalAspectInteceptor getLoggerInteceptor() {
        return new GlobalAspectInteceptor();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo( getApiInfo() )
                .host("127.0.0.1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("spring.cloud.gateway"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("spring-cloud-demo by harryChen, https://github.com/chxfantasy")
                .description("https://github.com/chxfantasy")
                .termsOfServiceUrl("https://github.com/chxfantasy")
                .contact("chxfantasy@gmail.com")
                .version("1.0")
                .build();
    }

    @Bean
    public ThreadPoolTaskExecutor globalTaskExecutor(
            @Value("${global.thread.pool.corePoolSize}") Integer corePoolSize,
            @Value("${global.thread.pool.maxPoolSize}") Integer maxPoolSize,
            @Value("${global.thread.pool.queueCapacity}") Integer queueCapacity
    ) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize( corePoolSize );
        executor.setMaxPoolSize( maxPoolSize );
        executor.setQueueCapacity( queueCapacity );
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadGroupName("globalTaskExecutor");
        executor.setRejectedExecutionHandler( new ThreadPoolExecutor.DiscardPolicy() );
        executor.initialize();
        return executor;
    }

}