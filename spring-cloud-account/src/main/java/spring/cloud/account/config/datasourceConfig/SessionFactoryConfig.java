package spring.cloud.account.config.datasourceConfig;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.github.pagehelper.PageHelper;

import javax.sql.DataSource;

@Configuration
public class SessionFactoryConfig {
	
	@Value("${mybatis.mapper-locations}")
	private String mapperLocations;
	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;
	@Value("${mybatis.config-location}")
	private String configLocation;
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource ) {
		try {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setDataSource(dataSource);
			sessionFactory.setTypeAliasesPackage(this.typeAliasesPackage);
			sessionFactory.setMapperLocations( new PathMatchingResourcePatternResolver().getResources(mapperLocations) );
			sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
			
			PageHelper pageHelper = new PageHelper();
			Properties props = new Properties();
			props.setProperty("reasonable", "false");
			props.setProperty("supportMethodsArguments", "true");
			props.setProperty("returnPageInfo", "check");
			props.setProperty("params", "count=countSql");
			pageHelper.setProperties(props);

			sessionFactory.setPlugins(new Interceptor[] { pageHelper });

			return sessionFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
