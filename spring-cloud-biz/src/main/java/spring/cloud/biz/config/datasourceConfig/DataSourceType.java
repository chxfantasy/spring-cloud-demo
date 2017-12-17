package spring.cloud.biz.config.datasourceConfig;

public enum DataSourceType {
	MOMENT("dataSource"), COMMENT("commentDataSource");
	
	private String beanName;

	DataSourceType(String dataSourceType) {
		this.beanName = dataSourceType;
	}
	
	@Override
	public String toString() {
		return this.beanName;
	}
	
	public String getType() {
		return this.beanName;
	}
	
}