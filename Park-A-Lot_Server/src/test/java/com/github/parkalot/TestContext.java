package com.github.parkalot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.github.parkalot.*")
@PropertySource("classpath:testConfig.properties")
public class TestContext {
	
	@Bean(name = "googlePlacesRestTemplate")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
