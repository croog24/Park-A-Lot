package com.googleapis.maps.place;

/**
 * The request object used when invoking Google Places API's.
 * 
 * @author Craig
 *
 */
public class GooglePlacesRequest {
	private String longitude;
	private String latitude;
	private int radius;

	/**
	 * Creates a new GooglePlacesRequest with a specified radius.
	 * 
	 * @param longitude the longitude coordinate of the request.
	 * @param latitude the latitude coordinate of the request.
	 * @param radius the radius to search within.
	 */
	public GooglePlacesRequest(String longitude, String latitude, int radius) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
	}

	/**
	 * Gets the coordinates of the request instance.
	 * 
	 * @return A <code>String</code> in the format of "<i>longitude,
	 *         latitude</i>".
	 */
	public String getCoords() {
		return longitude + "," + latitude;
	}

	/**
	 * Gets the radius of the request instance.
	 * 
	 * @return radius the radius.
	 */
	public int getRadius() {
		return radius;
	}
}