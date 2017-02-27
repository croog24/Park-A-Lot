package com.github.parkalot;

import org.lightcouch.CouchDbClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.github.parkalot")
@PropertySource("classpath:config.properties")
public class ParkALotAppContext {
	
	@Bean(name = "googlePlacesRestTemplate")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean(name = "couchDbClient")
	public CouchDbClient getCouchDbClient() {
		return new CouchDbClient();
	}
	
}
