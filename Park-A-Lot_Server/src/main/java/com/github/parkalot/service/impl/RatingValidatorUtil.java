package com.github.parkalot.service.impl;

import com.github.parkalot.model.Rating;

public final class RatingValidatorUtil {

    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    /** Checks if the given {@link Rating} value attribute is within the valid range. */
    public static boolean isValueValid(final int value) {
        return value >= Rating.MIN_VALUE && value <= Rating.MAX_VALUE;
    }

    /** Checks if the given hour is a searchable hour for the implemented Database hours. */
    public static boolean isHourValid(final int hour) {
        return MIN_HOUR <= hour && hour <= MAX_HOUR;
    }

    /** Checks if the supplied hour range is valid for the implemented Database's hours. */
    public static boolean isHourRangeValid(final int min, final int max) {
        return min < max && min >= MIN_HOUR && max <= MAX_HOUR;
    }
}
