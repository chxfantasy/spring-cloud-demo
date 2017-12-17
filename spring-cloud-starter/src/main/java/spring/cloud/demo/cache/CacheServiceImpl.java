package spring.cloud.demo.cache;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.google.common.base.Strings;

public class CacheServiceImpl implements CacheService{

	private static final Logger LOGGER = LoggerFactory.getLogger( CacheServiceImpl.class );

	private RedisTemplate<String, Object> redisTemplate;
	private StringRedisTemplate stringRedisTemplate;

	public CacheServiceImpl(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
		this.redisTemplate = redisTemplate;
		this.stringRedisTemplate = stringRedisTemplate;
	}
	
	public void putObject(String key, Object value) throws Exception{
		if ( Strings.isNullOrEmpty(key) || null==value) return;
		ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
		operations.set(key, value);
	}

	public void putObject(String key, Object value, int expire) throws Exception {
		if ( Strings.isNullOrEmpty(key) || null==value) return;
		ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
		operations.set(key, value);
		this.expire(key, expire);
	}

	public void deleteObjectByKey(String key) throws Exception {
		if (null==key) return;
		ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
		operations.getOperations().delete(key);
	}

	public Object getObject(String key) throws Exception {
		if ( Strings.isNullOrEmpty(key) ) {
			return null;
		}
		ValueOperations<String, Object> operations = this.redisTemplate.opsForValue();
		return operations.get(key);
	}

	public void putString(String key, String value) throws Exception {
		if ( Strings.isNullOrEmpty(key) || null==value) return;
		ValueOperations<String, String> operations = this.stringRedisTemplate.opsForValue();
		operations.set(key, value);
	}

	public void putString(String key, String value, int expire) throws Exception {
		this.putString(key, value);
		this.expire(key, expire);
	}

	public String getString(String key) throws Exception {
		if ( Strings.isNullOrEmpty(key) ) {
			return null;
		}
		ValueOperations<String, String> operations = this.stringRedisTemplate.opsForValue();
		return operations.get(key);
	}

	public void expire(String key, int expire) throws Exception {
		if ( Strings.isNullOrEmpty(key) ) {
			return ;
		}
		this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}

}
