package spring.cloud.biz.config.datasourceConfig;

import java.lang.annotation.*;

/**
 * @TargetDataSource
 * @author harry
 *
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TargetDataSource {
	DataSourceType value() default DataSourceType.MOMENT;
}