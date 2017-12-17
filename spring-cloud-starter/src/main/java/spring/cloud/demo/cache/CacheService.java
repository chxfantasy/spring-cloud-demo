package spring.cloud.demo.cache;

public interface CacheService {

	void putObject(String key, Object value) throws Exception;
	void putObject(String key, Object value, int expire) throws Exception;
	void deleteObjectByKey(String key) throws Exception;
	void expire(String key, int expire) throws Exception;
	Object getObject(String key) throws Exception;

	void putString(String key, String value) throws Exception;
	void putString(String key, String value, int expire) throws Exception;
	String getString(String key) throws Exception;

}
