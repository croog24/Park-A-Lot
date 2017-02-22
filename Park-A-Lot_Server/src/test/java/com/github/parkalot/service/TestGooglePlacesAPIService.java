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
import com.github.parkalot.service.GooglePlacesAPIService;
import com.googleapis.maps.place.GooglePlacesResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TestGooglePlacesAPIService {

	private MockRestServiceServer mockServer;

	@Autowired
	@Qualifier("googlePlacesRestTemplate")
	private RestTemplate restTemplate;

	@Autowired
	private GooglePlacesAPIService service;

	private GooglePlacesResponse sampleJSONResponse;
	private ObjectMapper objMapper;

	@Before
	public void setUp() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
		objMapper = new ObjectMapper();
		sampleJSONResponse = TestHelper.createJSONObjectFromResource("SampleGooglePlacesResponse.json",
				GooglePlacesResponse.class);
		service.setCurrentLocation("123", "123");
		service.setSelectedLocation("1111", "2222");
	}

	@Test
	public final void testGetNearbyParking() throws Exception {
		mockServer.expect(method(HttpMethod.GET))
				.andRespond(withSuccess(objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

		GooglePlacesResponse resp = service.getNearbyParking();

		mockServer.verify();

		assertTrue("OK".equals(resp.getStatus()));
	}

	@Test
	public final void testGetNearbyParkingWithRadius() throws Exception {
		mockServer.expect(method(HttpMethod.GET))
				.andRespond(withSuccess(objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

		GooglePlacesResponse resp = service.getNearbyParking(1000);

		mockServer.verify();

		assertTrue("OK".equals(resp.getStatus()));
	}

	@Test
	public final void testSelectedParking() throws Exception {
		mockServer.expect(method(HttpMethod.GET))
				.andRespond(withSuccess(objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

		GooglePlacesResponse resp = service.getParkingAtSelectedLocation();

		mockServer.verify();

		assertTrue("OK".equals(resp.getStatus()));
	}

	@Test
	public final void testSelectedParkingWithRadius() throws Exception {
		mockServer.expect(method(HttpMethod.GET))
				.andRespond(withSuccess(objMapper.writeValueAsString(sampleJSONResponse), MediaType.APPLICATION_JSON));

		GooglePlacesResponse resp = service.getParkingAtSelectedLocation(100);

		mockServer.verify();

		assertTrue("OK".equals(resp.getStatus()));
	}

}
