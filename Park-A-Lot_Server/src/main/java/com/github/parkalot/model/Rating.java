package com.github.parkalot.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

import org.lightcouch.Document;

public class Rating extends Document {

    private final String ratingId;
    private final String parkingLotId;
    private final int value;
    private final int hour;
    private final DayOfWeek dayOfWeek;
    // The unique device ID of the user
    private final String submittedByUserId;

    public Rating(final String parkingLotId, final int value, final String submittedByUserId) {
        this.ratingId = UUID.randomUUID().toString();
        // Set the internal CouchDB _id as well
        this.setId(ratingId);
        this.value = value;
        this.parkingLotId = parkingLotId;
        LocalDateTime dt = LocalDateTime.now();
        this.hour = dt.getHour();
        this.dayOfWeek = dt.getDayOfWeek();
        this.submittedByUserId = submittedByUserId;
    }

    public String getRatingId() {
        return ratingId;
    }

    public int getValue() {
        return value;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getSubmittedByUserId() {
        return submittedByUserId;
    }

    public int getHour() {
        return hour;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public String toString() {
        return String.format("ParkingLotId: %s RatingId: %s Value: %s SubmittedBy: %s @ %s %s",
                this.parkingLotId, this.ratingId, this.value, this.submittedByUserId,
                this.dayOfWeek, this.hour);
    }

}
