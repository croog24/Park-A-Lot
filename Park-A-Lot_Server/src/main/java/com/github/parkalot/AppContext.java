package com.github.parkalot;

import org.lightcouch.CouchDbClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.github.parkalot")
@PropertySource("classpath:config.properties")
@EnableWebMvc
public class AppContext {

    @Bean(name = "googlePlacesRestTemplate")
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CouchDbClient getCouchDbClient() {
        return new CouchDbClient();
    }

}
