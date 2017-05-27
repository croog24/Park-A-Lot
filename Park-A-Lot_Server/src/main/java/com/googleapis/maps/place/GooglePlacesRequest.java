package com.googleapis.maps.place;

/**
 * The request object used when invoking Google Places API's.
 * 
 * @author Craig
 *
 */
public class GooglePlacesRequest {
    private final CoordinatePair coords;
    private final int radius;

    /**
     * Creates a new GooglePlacesRequest with a specified radius.
     * 
     * @param coords the coordinates of the request
     * @param radius the radius to search within
     */
    public GooglePlacesRequest(final CoordinatePair coords, final int radius) {
        this.coords = coords;
        this.radius = radius;
    }

    /**
     * Gets the coordinates of the request instance in a Google request-friendly String format.
     * 
     * @return A <code>String</code> in the format of '<i>longitude, latitude</i>'.
     */
    public String getCoordString() {
        return this.coords.getLongitudeStr() + "," + this.coords.getLatitudeStr();
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
