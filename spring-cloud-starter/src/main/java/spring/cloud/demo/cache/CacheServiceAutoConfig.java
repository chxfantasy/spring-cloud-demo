package spring.cloud.demo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheServiceAutoConfig {
	
    @Bean("redisTemplate")
    @ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, Object> redisTemplate( @Autowired RedisConnectionFactory connectionFactory ) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer( new StringRedisSerializer() );
        //默认使用JDK的序列化
        template.setValueSerializer( new JdkSerializationRedisSerializer());

        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();

        return template;
    }

//    @Bean("stringRedisTemplate")
//    public StringRedisTemplate stringRedisTemplate(@Autowired(required=true) RedisConnectionFactory connectionFactory ) {
//        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
//        template.setDefaultSerializer(new StringRedisSerializer());
//
//        template.setEnableTransactionSupport(true);
//        template.afterPropertiesSet();
//
//        return template;
//    }

    @Bean
    @ConditionalOnMissingBean(CacheService.class)
    @ConditionalOnBean({RedisConnectionFactory.class})
    public CacheService cacheService(@Autowired StringRedisTemplate stringRedisTemplate,
                                         @Autowired RedisTemplate<String, Object> redisTemplate){
        stringRedisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setEnableTransactionSupport(true);
        return new CacheServiceImpl(redisTemplate, stringRedisTemplate);
    }

}