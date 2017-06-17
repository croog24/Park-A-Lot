package com.github.parkalot;

import org.lightcouch.CouchDbClient;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com.github.parkalot.*")
@PropertySource("classpath:testConfig.properties")
public class AbstractTestContext {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CouchDbClient getCouchDbClient() {
        return Mockito.mock(CouchDbClient.class);
    }
}
