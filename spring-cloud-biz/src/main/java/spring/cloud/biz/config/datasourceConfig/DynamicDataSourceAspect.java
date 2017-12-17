package spring.cloud.biz.config.datasourceConfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(-10) // 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( DynamicDataSourceAspect.class );
	
	@Before(value="@within(spring.cloud.biz.config.datasourceConfig.TargetDataSource) || @annotation(spring.cloud.biz.config.datasourceConfig.TargetDataSource)")
	public void changeDataSource(JoinPoint point) throws Throwable {
		Signature signature = point.getSignature();
		if ( ! (signature instanceof MethodSignature) ) {
			return;
		}
		
		Method method = ( (MethodSignature)signature ).getMethod();	//获取到的可能是父类的签名

        //被调用函数的注解
		Object realObject = point.getTarget();
		Method realClassMethod = realObject.getClass().getMethod(method.getName(), method.getParameterTypes());
		TargetDataSource targetDataSource = realClassMethod.getDeclaredAnnotation(TargetDataSource.class);
		if ( null != targetDataSource ) {
			LOGGER.debug( "[{}] -> set dataSource:{}", Thread.currentThread().getName(), targetDataSource.value().toString() );
			DynamicDataSourceContextHolder.setDataSourceType(targetDataSource.value());
			return ;
		}

        //被调用函数的实际类的注解
        targetDataSource = method.getDeclaringClass().getDeclaredAnnotation(TargetDataSource.class);
        if ( null != targetDataSource ) {
            LOGGER.debug( "[{}] -> set dataSource:{}", Thread.currentThread().getName(), targetDataSource.value().toString() );
            DynamicDataSourceContextHolder.setDataSourceType(targetDataSource.value());
            return ;
        }

        //父类函数的注解
        targetDataSource = method.getDeclaredAnnotation(TargetDataSource.class);	//最后再获取这个注解，可能是父类的注解，可能是realClassMethod
        if ( null != targetDataSource ) {
            LOGGER.debug( "[{}] -> set dataSource:{}", Thread.currentThread().getName(), targetDataSource.value().toString() );
            DynamicDataSourceContextHolder.setDataSourceType(targetDataSource.value());
            return ;
        }

		//父类的注解
		targetDataSource = method.getClass().getDeclaredAnnotation(TargetDataSource.class);	//类的注解
		if ( null != targetDataSource ) {
			LOGGER.debug( "[{}] -> set dataSource:{}", Thread.currentThread().getName(), targetDataSource.value().toString() );
			DynamicDataSourceContextHolder.setDataSourceType(targetDataSource.value());
			return ;
		}

	}
	
	@After(value="@within(spring.cloud.biz.config.datasourceConfig.TargetDataSource) || @annotation(spring.cloud.biz.config.datasourceConfig.TargetDataSource)")
	public void restoreDataSource(JoinPoint point) {
		DynamicDataSourceContextHolder.clearDataSourceType();
	}
}