package com.github.parkalot.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

import com.github.parkalot.ValidationException;
import com.github.parkalot.service.impl.RatingValidatorUtil;

public class Rating {

    public transient static final int MIN_VALUE = 1;
    public transient static final int MAX_VALUE = 5;

    private final String ratingId;
    private final String parkingLotId;
    private final int value;
    private final int hour;
    private final DayOfWeek dayOfWeek;
    // The unique device ID of the user
    private final String submittedByUserId;

    public Rating(final String parkingLotId, final int value, final String submittedByUserId)
            throws ValidationException {
        validateRatingValue(value);

        this.ratingId = UUID.randomUUID()
                            .toString();
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
