package com.github.parkalot.service;

import com.googleapis.maps.place.GooglePlacesResponse;

/**
 * A {@code protoype} scoped service layer to call Google's Places API.
 * 
 * @author Craig
 *
 */
public interface GooglePlacesAPIService {

	/**
	 * Sets the current location of the client.
	 * 
	 * @param latitude the latitude of the client.
	 * @param longitude the longitude of the client.
	 */
	public void setCurrentLocation(String latitude, String longitude);

	/**
	 * Sets the selected location of the client.
	 * 
	 * @param latitude the latitude of the client's selection.
	 * @param longitude the longitude of the client's selection.
	 */
	public void setSelectedLocation(String latitude, String longitude);

	/**
	 * Gets the parking spots nearby the current location using the default
	 * radius.
	 * 
	 * @return A {@code GooglePlacesResponse} of nearby parking spots.
	 */
	public GooglePlacesResponse getNearbyParking();

	/**
	 * Gets the parking spots nearby within the specified radius of the current
	 * location.
	 * 
	 * @param radius the radius of the request.
	 * @return A {@code GooglePlacesResponse} of nearby parking spots within
	 *         the specified radius.
	 */
	public GooglePlacesResponse getNearbyParking(int radius);

	/**
	 * Gets the parking spots at a specified location using the default radius.
	 * 
	 * @return A {@code GooglePlacesResponse} of parking spots near a specified
	 *         location.
	 */
	public GooglePlacesResponse getParkingAtSelectedLocation();

	/**
	 * Gets the parking spots at a specified location within the the specified
	 * radius.
	 * 
	 * @param radius of the request.
	 * @return A {@code GooglePlacesResponse} of nearby parking spots at a
	 *         specified location within the specified radius.
	 */
	public GooglePlacesResponse getParkingAtSelectedLocation(int radius);
}
