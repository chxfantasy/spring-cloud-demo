package spring.cloud.client.uitils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harry on 2017/5/11.
 */
public class ObjectParser {

    public static Map<String, String> objectToMap(Object object) {
        Map<String, String> map = new HashMap<>();
        if ( null == object ) return map;

        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object tmp = field.get(object);
                if ( null == tmp ) continue;
                map.put(field.getName(), tmp.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    public static <T extends Object> T mapToObject( Map<String, String> map, Class<T> clazz ) {
        if ( null == clazz ) return null;
        T obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return obj;
    }

}
