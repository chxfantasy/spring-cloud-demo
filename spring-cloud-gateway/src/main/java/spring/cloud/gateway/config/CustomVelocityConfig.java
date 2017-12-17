package spring.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import java.util.Properties;

/**
 * Created by Harry on 09/08/2017.
 */
@Configuration
public class CustomVelocityConfig {

    @Bean
    public VelocityConfigurer velocityConfig() {
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoader( new DefaultResourceLoader());
        velocityConfigurer.setResourceLoaderPath("classpath:/templates/");
        Properties properties = new Properties();
        properties.put("input.encoding", "utf-8");
        properties.put("output.encoding", "utf-8");
        velocityConfigurer.setVelocityProperties(properties);
        return velocityConfigurer;
    }
    @Bean
    public VelocityLayoutViewResolver velocityViewResolver() {
        VelocityLayoutViewResolver viewResolver = new VelocityLayoutViewResolver();
//        viewResolver.setViewClass(VelocityToolboxView.class);
        viewResolver.setLayoutUrl("layout/default.vm");
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".vm");
        viewResolver.setContentType("text/html;charset=utf-8");
//        viewResolver.setToolboxConfigLocation("/WEB-INF/toolbox.xml");
        return viewResolver;
    }

}
