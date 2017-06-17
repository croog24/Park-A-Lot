package com.github.parkalot;

import org.lightcouch.CouchDbClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class AppContext {

    @Bean
    public CouchDbClient getCouchDbClient() {
        return new CouchDbClient();
    }

}
