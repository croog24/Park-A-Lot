package com.github.parkalot;

import org.lightcouch.CouchDbClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.github.parkalot")
public class TestContext {
    
    @MockBean
    private CouchDbClient couchDbCLient;
}
