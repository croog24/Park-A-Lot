package com.github.parkalot.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.parkalot.TestContext;
import com.github.parkalot.service.GooglePlacesApiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
@EnableWebMvc
public class TestLocationController {
    
    @Autowired
    private WebApplicationContext ctx;
    
    @MockBean
    private GooglePlacesApiService mockGooglePlacesApiService;
    
    private MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }
    
    @Test
    public void test() {
        // TODO: Implement
    }
    
}
