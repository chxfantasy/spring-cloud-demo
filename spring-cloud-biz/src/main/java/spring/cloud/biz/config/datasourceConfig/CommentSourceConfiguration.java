package spring.cloud.biz.config.datasourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CommentSourceConfiguration {
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.comment")
	public DataSource commentDataSource() {
	    return DataSourceBuilder.create().type(DruidDataSource.class).build();
	}
	
	@Bean
	public DataSourceTransactionManager commentTxManager( @Qualifier("commentDataSource") DataSource appDataSource ) {
		return new DataSourceTransactionManager(appDataSource);
	}
	
}