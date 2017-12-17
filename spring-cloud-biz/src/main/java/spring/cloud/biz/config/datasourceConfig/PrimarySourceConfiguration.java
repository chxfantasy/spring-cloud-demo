package spring.cloud.biz.config.datasourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class PrimarySourceConfiguration {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		DruidDataSource dataSource = (DruidDataSource) DataSourceBuilder.create().type(DruidDataSource.class).build();
	    return dataSource;
	}
	
	@Bean
	@Primary
	public DataSourceTransactionManager txManager( DataSource dataSource ) {
		return new DataSourceTransactionManager(dataSource);
	}
	
}
