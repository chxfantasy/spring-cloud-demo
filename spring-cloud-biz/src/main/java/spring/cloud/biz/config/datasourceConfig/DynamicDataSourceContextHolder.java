package spring.cloud.biz.config.datasourceConfig;

public class DynamicDataSourceContextHolder {
	
	private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<DataSourceType>();
	
	public static void setDataSourceType(DataSourceType dataSourceType) {
		contextHolder.set(dataSourceType);
	}

	public static DataSourceType getDataSourceType() {
		DataSourceType dataSourceType = contextHolder.get();
		if ( null == dataSourceType ) {
			return DynamicDataSource.getDefaultDatasource();
		}
		return dataSourceType;
	}

	public static void clearDataSourceType() {
		contextHolder.remove();
	}
}
