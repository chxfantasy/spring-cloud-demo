package spring.cloud.client.uitils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtil {
	public static String toJSONString(Object obj) {
		return JSON.toJSONStringWithDateFormat(obj,
                "yyyy-MM-dd HH:mm:ss",
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect
                );
	}
}
