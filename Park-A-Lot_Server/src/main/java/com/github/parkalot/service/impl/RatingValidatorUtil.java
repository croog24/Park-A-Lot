package com.github.parkalot.service.impl;

import com.github.parkalot.model.Rating;

/**
 * Provides common {@link Rating} validation methods.
 * 
 * @author Craig
 *
 */
public final class RatingValidatorUtil {

    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    /**
     * Checks if the given {@link Rating} value attribute is within the valid range.
     */
    public static boolean isValueValid(final int value) {
        return value >= Rating.MIN_VALUE && value <= Rating.MAX_VALUE;
    }

    /**
     * Checks if the given hour is a searchable hour for the implemented Database hours.
     * 
     * @param hour the hour to check
     * @return {@code true} if the provided hour meets the valid min/max range specifications
     */
    public static boolean isHourValid(final int hour) {
        return MIN_HOUR <= hour && hour <= MAX_HOUR;
    }

    /**
     * Checks if the supplied hour range is valid for the implemented Database's hours.
     * 
     * @param min the minimum hour used
     * @param max the maximum hour used
     * @return {@code true} if the provided hours meet the valid min/max range specifications
     */
    public static boolean isHourRangeValid(final int min, final int max) {
        return min < max && min >= MIN_HOUR && max <= MAX_HOUR;
    }
}
