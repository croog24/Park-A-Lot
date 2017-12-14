package com.github.parkalot.request;

import java.time.DayOfWeek;
import java.util.Objects;

import com.github.parkalot.ValidationException;

/**
 * Request criteria of a {@link Rating} related request.
 * 
 * @author Craig
 *
 */
public class QueryRequest {
    private final QueryType queryType;
    private final String parkingLotId;
    private final DayOfWeek weekday;
    private final Integer minHour;
    private final Integer maxHour;

    /** They matching query search type given the specified criteria. */
    public enum QueryType {
        HOUR, HOUR_RANGE, WEEKDAY, PARKING_LOT_ID
    }

    public QueryRequest(final String parkingLotId,
                        final String weekday,
                        final Integer minHour,
                        final Integer maxHour)
            throws ValidationException {
        this.queryType = determineQueryType(parkingLotId, weekday, minHour, maxHour);
        this.parkingLotId = Objects.requireNonNull(parkingLotId);
        this.weekday = getWeekDay(weekday);
        this.minHour = minHour;
        this.maxHour = maxHour;
    }

    private DayOfWeek getWeekDay(final String weekday) throws ValidationException {
        try {
            return DayOfWeek.valueOf(weekday);
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                                          String.format("Weekday value %s is not a valid weekday", weekday));
        }
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public String getParkingLotId() {
        return parkingLotId;
    }

    public DayOfWeek getWeekday() {
        return weekday;
    }

    public Integer getMinHour() {
        return minHour;
    }

    public Integer getMaxHour() {
        return maxHour;
    }

    private QueryType determineQueryType(final String parkingLotId,
                                         final String weekday,
                                         final Integer minHour,
                                         final Integer maxHour) {
        // Default query type
        QueryType queryType = QueryType.PARKING_LOT_ID;

        if (weekday != null) {
            queryType = QueryType.WEEKDAY;
        } else if (bothHoursPresent(minHour, maxHour)) {
            queryType = QueryType.HOUR_RANGE;
        } else if (oneHourPresent(minHour, maxHour)) {
            queryType = QueryType.HOUR;
        }

        return queryType;
    }

    private boolean bothHoursPresent(final Integer min, final Integer max) {
        return min != null && max != null;
    }

    private boolean oneHourPresent(final Integer min, final Integer max) {
        return min != null || max != null;
    }

    @Override
    public String toString() {
        return "QueryRequest [queryType=" + queryType + ", parkingLotId=" + parkingLotId
                + ", weekday=" + weekday + ", minHour=" + minHour + ", maxHour=" + maxHour + "]";
    }

}
