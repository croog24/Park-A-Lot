package com.googleapis.maps.place;

/**
 * A pair of latitude and longitude coordinates.
 * 
 * @author Craig
 *
 */
public class CoordinatePair {
    private static final Double MIN_LAT = 0.0;
    private static final Double MAX_LAT = 90.0;
    private static final Double MIN_LON = 0.0;
    private static final Double MAX_LON = 180.0;

    private final Double latitude;
    private final Double longitude;

    public CoordinatePair(final Double latitude, final Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public String getLatitudeStr() {
        return String.valueOf(this.latitude);
    }

    public String getLongitudeStr() {
        return String.valueOf(this.longitude);
    }

    /**
     * Checks if the coordinates of the pair are valid.
     * 
     * @return {@code true} if valid
     */
    public boolean isValidRange() {
        return (this.latitude < MIN_LAT 
               || this.latitude > MAX_LAT 
               || this.longitude < MIN_LON 
               || this.longitude > MAX_LON);
    }
}
