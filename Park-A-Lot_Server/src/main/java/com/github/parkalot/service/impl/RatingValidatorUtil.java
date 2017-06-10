package com.github.parkalot.service.impl;

import com.github.parkalot.model.Rating;

public final class RatingValidatorUtil {

    private static final int DEFAULT_MIN_HOUR = 0;
    private static final int DEFAULT_MAX_HOUR = 23;
    private static final int MIN_RATING_VALUE = 1;
    private static final int MAX_RATING_VALUE = 5;

    /**
     * Checks if the given {@link Rating} value attribute is within the valid range.
     */
    public static boolean isValueValid(final Integer value) {
        return value >= MIN_RATING_VALUE && value <= MAX_RATING_VALUE;
    }

    /**
     * Checks if the given hour is a searchable hour for the implemented Database hours.
     * 
     * @param hour the hour to check
     * @return {@code true} if the provided hour meets the valid min/max range specifications
     */
    public static boolean isHourValid(final Integer hour) {
        return DEFAULT_MIN_HOUR <= hour && hour <= DEFAULT_MAX_HOUR;
    }

    /**
     * Checks if the supplied hour range is valid for the implemented Database's hours.
     * 
     * @param min the minimum hour used
     * @param max the maximum hour used
     * @return {@code true} if the provided hours meet the valid min/max range specifications
     */
    public static boolean isHourRangeValid(final Integer min, final Integer max) {
        return min < max || min >= DEFAULT_MIN_HOUR || max <= DEFAULT_MAX_HOUR;
    }
}
