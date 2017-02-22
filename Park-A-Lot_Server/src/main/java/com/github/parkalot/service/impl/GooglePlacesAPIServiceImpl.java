package com.github.parkalot.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.parkalot.service.GooglePlacesAPIService;
import com.googleapis.maps.place.GooglePlacesRequest;
import com.googleapis.maps.place.GooglePlacesResponse;

@Service
@Scope("prototype")
public final class GooglePlacesAPIServiceImpl implements GooglePlacesAPIService {

	private static final Logger LOGGER = Logger.getLogger(GooglePlacesAPIServiceImpl.class);
	private static final int DEFAULT_RADIUS = 1000;

	@Value("${google.api.maps.place.key}")
	private String API_KEY;
	@Value("${google.api.maps.place.url}")
	private String MAPS_API_URI;

	private static ObjectMapper objMapper = new ObjectMapper();
	private String currLatitude;
	private String currLongitude;
	private String selectedLatitude;
	private String selectedLongitude;

	@Autowired
	@Qualifier("googlePlacesRestTemplate")
	private RestTemplate restTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCurrentLocation(String latitude, String longitude) {
		this.currLatitude = latitude;
		this.currLongitude = longitude;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelectedLocation(String latitude, String longitude) {
		this.selectedLatitude = latitude;
		this.selectedLongitude = longitude;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GooglePlacesResponse getNearbyParking() {
		LOGGER.debug("Creating GetNearbyParking() request");
		GooglePlacesRequest req = new GooglePlacesRequest(this.currLongitude, this.currLatitude, DEFAULT_RADIUS);

		return sendRequest(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GooglePlacesResponse getNearbyParking(int radius) {
		LOGGER.debug("Creating GetNearbyParking(radius) request");
		GooglePlacesRequest req = new GooglePlacesRequest(this.currLongitude, this.currLatitude, radius);

		return sendRequest(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GooglePlacesResponse getParkingAtSelectedLocation() {
		LOGGER.debug("Creating GetParkingAtSelectedLocation() request");
		GooglePlacesRequest req = new GooglePlacesRequest(this.selectedLongitude, this.selectedLatitude,
				DEFAULT_RADIUS);

		return sendRequest(req);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GooglePlacesResponse getParkingAtSelectedLocation(int radius) {
		LOGGER.debug("Creating GetParkingAtSelectedLocation(radius) request");
		GooglePlacesRequest req = new GooglePlacesRequest(this.selectedLongitude, this.selectedLatitude, radius);

		return sendRequest(req);
	}

	/**
	 * Sends the GooglePlacesRequest with the specified parameters.
	 * 
	 * @param request the request to send.
	 * @return GooglePlacesResponse a response containing a list of parking lots
	 *         and their information.
	 */
	private GooglePlacesResponse sendRequest(GooglePlacesRequest request) {
		GooglePlacesResponse resp = new GooglePlacesResponse();
		try {
			final String URI = generateURI(request);
			LOGGER.info("Sending Request: " + URI);
			resp = restTemplate.getForObject(URI, GooglePlacesResponse.class);

			LOGGER.info("Received Response: " + objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resp));
		} catch (Exception e) {
			final String errorMsg = String.format("Failure connecting to GooglePlaces API: %s", e.getMessage());
			LOGGER.error(errorMsg);
			resp.setErrorMsg(errorMsg);
		}
		return resp;
	}

	/**
	 * Generates the full URI of the RESTful API call to invoke for the Google
	 * Places API.
	 * 
	 * @param request the request to use.
	 * @return String a String URI containing the default necessary parameters
	 *         and optional parameters of the Google Places API calls.
	 */
	private String generateURI(GooglePlacesRequest request) {
		LOGGER.debug("Generating GooglePlaces API URI");
		return String.format("%sjson?location=%s&radius=%s&type=parking&key=%s", 
				MAPS_API_URI, 
				request.getCoords(),
				request.getRadius(), 
				API_KEY);
	}

}