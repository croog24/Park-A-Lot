package com.github.parkalot.service;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.parkalot.TestContext;
import com.github.parkalot.TestHelper;
import com.github.parkalot.ValidationException;
import com.github.parkalot.service.GooglePlacesApiService;
import com.googleapis.maps.place.GooglePlacesResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class TestGooglePlacesAPIService {
    private final static ObjectMapper objMapper = new ObjectMapper();

    private MockRestServiceServer mockServer;

    @Autowired
    @Qualifier("googlePlacesRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private GooglePlacesApiService service;

    private GooglePlacesResponse sampleJSONResponse;

    @Before
    public void setUp() throws ValidationException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        sampleJSONResponse = TestHelper.createJSONObjectFromResource(
                "SampleGooglePlacesResponse.json", GooglePlacesResponse.class);
        service.setCurrentLocation(123.0, 123.0);
        service.setSelectedLocation(1111.0, 2222.0);
    }
    
    @Test
    public void testGetNearbyParking() throws Exception {
        mockServer.expect(method(HttpMethod.GET)).andRespond(withSuccess(
                objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

        final GooglePlacesResponse resp = service.getNearbyParking();

        mockServer.verify();

        assertTrue("OK".equals(resp.getStatus()));
    }

    @Test
    public void testGetNearbyParkingWithRadius() throws Exception {
        mockServer.expect(method(HttpMethod.GET)).andRespond(withSuccess(
                objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

        final GooglePlacesResponse resp = service.getNearbyParking(1000);

        mockServer.verify();

        assertTrue("OK".equals(resp.getStatus()));
    }

    @Test
    public void testSelectedParking() throws Exception {
        mockServer.expect(method(HttpMethod.GET)).andRespond(withSuccess(
                objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

        final GooglePlacesResponse resp = service.getParkingAtSelectedLocation();

        mockServer.verify();

        assertTrue("OK".equals(resp.getStatus()));
    }

    @Test
    public void testSelectedParkingWithRadius() throws Exception {
        mockServer.expect(method(HttpMethod.GET)).andRespond(withSuccess(
                objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

        final GooglePlacesResponse resp = service.getParkingAtSelectedLocation(100);

        mockServer.verify();

        assertTrue("OK".equals(resp.getStatus()));
    }

}
