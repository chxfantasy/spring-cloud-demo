package spring.cloud.account.config.datasourceConfig;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class PrimarySourceConfiguration {
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		DruidDataSource dataSource = (DruidDataSource) DataSourceBuilder.create().type(DruidDataSource.class).build();
	    return dataSource;
	}
	
	@Bean
	public DataSourceTransactionManager txManager( DataSource dataSource ) {
		return new DataSourceTransactionManager(dataSource);
	}
	
}
