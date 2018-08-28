package org.my.app;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.my.service.IAutoSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(classes = AppIntegrationTest.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "org.my")
public class AppIntegrationTest {

	@Autowired
	private IAutoSuggestService autoSuggestService;
	
	@Test
	public void testSuggestCities() {
		String result  = autoSuggestService.suggestCities("MEE", 2);
		assertNotNull(result);
		assertEquals("Meerut\r\n", result);
	}
	
}
