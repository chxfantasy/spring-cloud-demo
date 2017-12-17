package spring.cloud.account;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BaseIntegrationTest {
	
	@Autowired private RestTemplateBuilder restTemplateBuilder;
	@Value("${server.port}")
	private String definedPort;
	
	protected RestTemplate restTemplate;
	protected String url;
	
	@Before
	public void setupBeforeClass() {
		if( null == url ) {
			url = "http://127.0.0.1:" + definedPort;
			restTemplate = restTemplateBuilder.build();
		}
	}
	
}
