package spring.cloud.account.controller;

import org.junit.Assert;
import org.junit.Test;

import spring.cloud.account.BaseIntegrationTest;

public class InternationalDemoTest extends BaseIntegrationTest {
	
	@Test
	public void zh() {
		Assert.assertEquals("测试", restTemplate.getForEntity( url + "/in/lang?lang=zh_CN", String.class).getBody());
	}
	
	@Test
	public void en() {
		Assert.assertEquals("test", restTemplate.getForEntity( url + "/in/lang?lang=en_US", String.class).getBody());
	}
}
