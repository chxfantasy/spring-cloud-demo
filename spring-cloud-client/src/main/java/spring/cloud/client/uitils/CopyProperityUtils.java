package spring.cloud.client.uitils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry on 17/3/18.
 */
public class CopyProperityUtils {
    public static void copyProperiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static void copyAllProperies(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void copyPropertiesWithIgnore(Object source, Object target, String...ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static String[] getNullPropertyNames(Object source) {
        List<String> nullPropertyNames = new ArrayList<String>();
        BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        PropertyDescriptor[] dess = wrappedSource.getPropertyDescriptors();
        if ( null != dess && dess.length > 0 ) {
            for ( int i = 0; i < dess.length; i++ ) {
                PropertyDescriptor des = dess[i];
                if ( null == des ) continue ;
                String propertyName = des.getName();
                if ( null == wrappedSource.getPropertyValue(propertyName) ) {
                    nullPropertyNames.add( propertyName );
                }

            }
        }
        return nullPropertyNames.toArray( new String[nullPropertyNames.size()] );
    }

    public static String[] getNotNullPropertyNames(Object source) {
        List<String> notNullPropertyNames = new ArrayList<String>();
        BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        PropertyDescriptor[] dess = wrappedSource.getPropertyDescriptors();
        if ( null != dess && dess.length > 0 ) {
            for ( int i = 0; i < dess.length; i++ ) {
                PropertyDescriptor des = dess[i];
                if ( null == des ) continue ;
                String propertyName = des.getName();
                if ( null != wrappedSource.getPropertyValue(propertyName) ) {
                    notNullPropertyNames.add( propertyName );
                }
            }
        }
        return notNullPropertyNames.toArray( new String[notNullPropertyNames.size()] );
    }
}
