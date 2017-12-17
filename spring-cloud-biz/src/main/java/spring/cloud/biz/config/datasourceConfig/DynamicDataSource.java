package spring.cloud.biz.config.datasourceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
@AutoConfigureAfter(value={PrimarySourceConfiguration.class, CommentSourceConfiguration.class})
public class DynamicDataSource extends AbstractRoutingDataSource{
	
	private static final Logger LOGGER = LoggerFactory.getLogger( DynamicDataSource.class );
	
	@Autowired private Map<String, DataSource> targetDataSources;
	
	private static DataSourceType DEFAULT_DATASOURCE = DataSourceType.MOMENT;
	
	@PostConstruct
	public void setUpTargetDataSources() {
		Iterator<String> keyIte = targetDataSources.keySet().iterator();
		Map<Object, Object> dataSources =  new HashMap<>();
		while (keyIte.hasNext()) {
			String key = keyIte.next();
			dataSources.put(key, targetDataSources.get(key));
		}
		this.setTargetDataSources(dataSources);
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSource_beanName = DynamicDataSourceContextHolder.getDataSourceType().toString();
		return dataSource_beanName;
	}

	public static DataSourceType getDefaultDatasource() {
		return DEFAULT_DATASOURCE;
	}
	
}