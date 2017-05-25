package com.github.parkalot.controller;

import java.time.DayOfWeek;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.parkalot.ValidationException;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.ParkingLotService;
import com.github.parkalot.service.RatingService;

/**
 * Entry point for all requests involving {@link Rating} retrieval/adding.
 * 
 * @author Craig
 */
@RestController
@RequestMapping("/rating/{parking-lot-id}")
public class RatingController {

    private static final Logger LOGGER = Logger.getLogger(RatingController.class);
    private static final int DEFAULT_MIN_HOUR = 0;
    private static final int DEFAULT_MAX_HOUR = 23;
    private static final int MIN_RATING_VALUE = 1;
    private static final int MAX_RATING_VALUE = 5;

    private final RatingService ratingService;
    private final ParkingLotService parkingLotService;

    @Autowired
    public RatingController(final RatingService ratingService,
            final ParkingLotService parkingLotService) {
        this.ratingService = ratingService;
        this.parkingLotService = parkingLotService;
    }

    /**
     * Main GET request handler for {@link Rating} types. Handles several optional filters, but a
     * {@code ParkingLotId} must be provided. <b>Note</b>: if both {@code minHour} and
     * {@code maxHour} are provided, if will filter by the hour range.
     * 
     * @param parkingLotId the mandatory parking lot ID.
     * @param weekday the optional <b>valid</b> weekday
     * @param minHour the optional <b>valid</b> min hour
     * @param maxHour the optional <b>valid</b> max hour
     * @return A {@code List} of {@link Rating}s with {@link HttpStatus} {@code 200 OK} or
     *         {@code 400 BAD_REQUEST}/{@code 500 INTERNAL_SERVER_ERROR} if some error occurred
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getRatings(@PathVariable("parking-lot-id") final String parkingLotId,
            @RequestParam(value = "weekday", required = false) final String weekday,
            @RequestParam(value = "min-hour", required = false) final Integer minHour,
            @RequestParam(value = "max-hour", required = false) final Integer maxHour) {
        LOGGER.debug("Start of RatingController.getRatings()");

        ResponseEntity<?> response = null;

        try {
            if (weekday != null) {
                response = handleGetRatingsByWeekday(parkingLotId, weekday);
            } else if (minHour != null && maxHour != null) {
                response = handleGetRatingsBetweenHours(parkingLotId, minHour, maxHour);
            } else if (minHour != null || maxHour != null) {
                if (minHour != null) {
                    response = handleGetRatingsByHour(parkingLotId, minHour);
                } else {
                    response = handleGetRatingsByHour(parkingLotId, maxHour);
                }
            } else {
                response = handleGetRatingsByParkingLotId(parkingLotId);
            }

        } catch (ValidationException e) {
            response = ResponseEntityUtils.createValidationExcResponse(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntityUtils.createUnhandledExcResponse(e.getMessage());
        }

        LOGGER.debug("End of RatingController.getRatings()");
        return response;
    }

    /**
     * Main PUT request handler for {@link Rating} types.
     * 
     * @param parkingLotId the mandatory parking lot ID
     * @param value the value of the {@code Rating}
     * @param submittedBy the unique device ID of the user submitting the {@code Rating}
     * @return A {@link ResponseEntity} with {@link HttpStatus} {@code 201} if successful or
     *         {@code 400}/{@code 500} if some error occurred
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> addRating(
            @PathVariable("parking-lot-id") final String parkingLotId,
            @RequestParam(value = "value") final Integer value,
            @RequestParam(value = "submitted-by") final String submittedBy) {
        LOGGER.debug("Start of RatingController.addRating()");
        ResponseEntity<String> response = null;

        try {
            response = handleAddRating(parkingLotId, value, submittedBy);
        } catch (ValidationException e) {
            response = ResponseEntityUtils.createValidationExcResponse(e.getMessage());
        } catch (Exception e) {
            response = ResponseEntityUtils.createUnhandledExcResponse(e.getMessage());
        }

        LOGGER.debug("End of RatingController.addRating()");
        return response;
    }

    /**
     * Checks if the given {@link Rating} value attribute is within the valid range.
     */
    private boolean isValueValid(final int value) {
        return value >= MIN_RATING_VALUE && value <= MAX_RATING_VALUE;
    }

    /** Handles requests with no additional filters. */
    private ResponseEntity<?> handleGetRatingsByParkingLotId(final String parkingLotId)
            throws Exception {
        return new ResponseEntity<List<Rating>>(ratingService.getRatingsByParkingLot(parkingLotId),
                HttpStatus.OK);
    }

    /** Handles requests with Weekday parameter present. */
    private ResponseEntity<List<Rating>> handleGetRatingsByWeekday(final String parkingLotId,
            final String weekday) throws Exception {
        DayOfWeek dayEnum;
        try {
            dayEnum = DayOfWeek.valueOf(weekday.toUpperCase());
        } catch (Exception e) {
            throw new ValidationException(
                    String.format("Weekday value %s is not a valid weekday", weekday));
        }

        return new ResponseEntity<List<Rating>>(
                ratingService.getRatingsByDayOfWeek(parkingLotId, dayEnum), HttpStatus.OK);
    }

    /** Handles requests with a single hour provided. */
    private ResponseEntity<List<Rating>> handleGetRatingsByHour(final String parkingLotId,
            final int hour) throws Exception {
        if (!isHourRangeValid(hour)) {
            throw new Exception("Invalid hour specified!");
        }

        return new ResponseEntity<List<Rating>>(ratingService.getRatingsByHour(parkingLotId, hour),
                HttpStatus.OK);
    }

    /** Handles requests with a specified hour range. */
    private ResponseEntity<List<Rating>> handleGetRatingsBetweenHours(final String parkingLotId,
            final int minHour, final int maxHour) throws Exception {
        if (!isHourRangeValid(minHour, maxHour)) {
            throw new Exception("Invalid hour range!");
        }
        return new ResponseEntity<List<Rating>>(
                ratingService.getRatingsBetweenHours(parkingLotId, minHour, maxHour),
                HttpStatus.OK);
    }

    /**
     * Checks if the supplied hour range is valid for the implemented Database's hours.
     * 
     * @param min the minimum hour used
     * @param max the maximum hour used
     * @return {@code true} if the provided hours meet the valid min/max range specifications
     */
    private boolean isHourRangeValid(final int min, final int max) {
        return min < max || min >= DEFAULT_MIN_HOUR || max <= DEFAULT_MAX_HOUR;
    }

    /**
     * Checks if the given hour is a searchable hour for the implemented Database hours.
     * 
     * @param hour the hour to check
     * @return {@code true} if the provided hour meets the valid min/max range specifications
     */
    private boolean isHourRangeValid(final int hour) {
        return DEFAULT_MIN_HOUR <= hour && hour <= DEFAULT_MAX_HOUR;
    }

    /** Handles requests to add a {@link Rating}. */
    private ResponseEntity<String> handleAddRating(final String parkingLotId, final int value,
            final String submittedBy) throws Exception {
        if (!isValueValid(value)) {
            throw new ValidationException("Unexpected Rating value provided!");
        }

        final Rating r = new Rating(parkingLotId, value, submittedBy);
        final boolean isCreated = ratingService.addRating(r);
        if (isCreated) {
            // Update the containing parking lot
            final ParkingLot p = parkingLotService.getParkingLotById(parkingLotId);
            if (p == null) {
                throw new Exception("ParkingLot not found!");
            }

            p.getRatingList().add(r.getRatingId());

            final boolean isLotUpdated = parkingLotService.updateParkingLot(p);
            if (!isLotUpdated) {
                // Remove orphan Rating and throw error
                ratingService.deleteRating(r);
                throw new Exception("Unable to update the containing Parking Lot!");
            }
        } else {
            throw new Exception("Rating not successfully added, please try again!");
        }

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
