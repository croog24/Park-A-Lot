package com.github.parkalot.service;

import com.github.parkalot.ValidationException;
import com.googleapis.maps.place.GooglePlacesResponse;

/**
 * A {@code prototype} scoped service layer to call Google's Places API.
 * 
 * @author Craig
 */
public interface GooglePlacesApiService {

    /**
     * Sets the current location of the client.
     * 
     * @param latitude the latitude of the client
     * @param longitude the longitude of the client
     */
    void setCurrentLocation(final Double latitude, final Double longitude) throws ValidationException;

    /**
     * Sets the selected location of the client.
     * 
     * @param latitude the latitude of the client's selection
     * @param longitude the longitude of the client's selection
     */
    void setSelectedLocation(final Double latitude, final Double longitude) throws ValidationException;

    /**
     * Gets the parking spots nearby the current location using the default radius.
     * 
     * @return A {@code GooglePlacesResponse} of nearby parking spots
     */
    GooglePlacesResponse getNearbyParking();

    /**
     * Gets the parking spots nearby within the specified radius of the current location.
     * 
     * @param radius the radius of the request
     * @return A {@code GooglePlacesResponse} of nearby parking spots within the specified radius
     */
    GooglePlacesResponse getNearbyParking(final int radius);

    /**
     * Gets the parking spots at a specified location using the default radius.
     * 
     * @return A {@code GooglePlacesResponse} of parking spots near a specified location
     */
    GooglePlacesResponse getParkingAtSelectedLocation();

    /**
     * Gets the parking spots at a specified location within the the specified radius.
     * 
     * @param radius of the request
     * @return A {@code GooglePlacesResponse} of nearby parking spots at a specified location within
     *         the specified radius
     */
    GooglePlacesResponse getParkingAtSelectedLocation(final int radius);
}
