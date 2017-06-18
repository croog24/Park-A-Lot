package com.github.parkalot.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

import org.lightcouch.Document;

import com.github.parkalot.ValidationException;
import com.github.parkalot.service.impl.RatingValidatorUtil;

public class Rating extends Document {

    public transient final static int MIN_VALUE = 1;
    public transient final static int MAX_VALUE = 5;

    private final String ratingId;
    private final String parkingLotId;
    private final Integer value;
    private final Integer hour;
    private final DayOfWeek dayOfWeek;
    // The unique device ID of the user
    private final String submittedByUserId;

    public Rating(final String parkingLotId, final Integer value, final String submittedByUserId)
            throws ValidationException {
        validateRatingValue(value);

        this.ratingId = UUID.randomUUID()
                            .toString();
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

    public Integer getValue() {
        return value;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public String getSubmittedByUserId() {
        return submittedByUserId;
    }

    public Integer getHour() {
        return hour;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    private void validateRatingValue(int value) throws ValidationException {
        if (!RatingValidatorUtil.isValueValid(value)) {
            throw new ValidationException("Unexpected Rating value provided");
        }
    }

    @Override
    public String toString() {
        return String.format("ParkingLotId: %s RatingId: %s Value: %s SubmittedBy: %s @ %s %s",
                this.parkingLotId, this.ratingId, this.value, this.submittedByUserId,
                this.dayOfWeek, this.hour);
    }

}
