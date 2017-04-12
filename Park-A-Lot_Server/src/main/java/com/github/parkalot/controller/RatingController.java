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
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.RatingService;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/rating/{parking-lot-id}")
public class RatingController {
	
	private static final Logger LOGGER = Logger.getLogger(RatingController.class);
	private static final int DEFAULT_MIN_HOUR = 0;
	private static final int DEFAULT_MAX_HOUR = 23;
	private static final int MIN_RATING_VALUE = 1;
	private static final int MAX_RATING_VALUE = 5;

	@Autowired
	private RatingService ratingService;

	/**
	 * Main GET request handler for {@link Rating} types. Handles several
	 * optional filters, but a {@code ParkingLotId} must be provided.
	 * <b>Note</b>: if both {@code minHour} and {@code maxHour} are provided, if
	 * will filter by the hour range.
	 * 
	 * @param parkingLotId the mandatory parking lot ID.
	 * @param weekday the optional <b>valid</b> weekday
	 * @param minHour the optional <b>valid</b> min hour.
	 * @param maxHour the optional <b>valid</b> max hour.
	 * @return A {@code List} of {@link Rating}s with {@link HttpStatus}
	 *         {@code 200 OK} or {@code 400 BAD_REQUEST}/{@code 500 INTERNAL_SERVER_ERROR} if some error occurred.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getRatings(
			@PathVariable("parking-lot-id") String parkingLotId, 
			@RequestParam(value = "weekday", required = false) String weekday,
			@RequestParam(value = "min-hour", defaultValue = "-1") int minHour, 
			@RequestParam(value = "max-hour", defaultValue = "-1") int maxHour) {
		LOGGER.debug("Start of RatingController.getRatings()");
		
		ResponseEntity<List<Rating>> response = null;
		
		try {
			if (weekday != null) {
				response = handleGetRatingsByWeekday(parkingLotId, weekday);
			}
			else if (isBothHourParamsPresent(minHour, maxHour)) {
				response = handleGetRatingsBetweenHours(parkingLotId, minHour, maxHour);
			}
			else if (isHourParamPresent(minHour, maxHour)) {
				if(minHour != -1) {
					response = handleGetRatingsByHour(parkingLotId, minHour);
				}
				else {
					response = handleGetRatingsByHour(parkingLotId, maxHour);
				}
			}
			else {
				response = handleGetRatingsByParkingLotId(parkingLotId);
			}

		} catch (ValidationException e) {
			response = handleValidationException(e.getMessage());
		} catch (Exception e) {
			response = handleUnexpectedException(e.getMessage());
		}
		
		LOGGER.debug("End of RatingController.getRatings()");
		return response;
	}
	
	/**
	 * Main PUT request handler for {@link Rating} types.
	 * 
	 * @param parkingLotId the mandatory parking lot ID.
	 * @param value the value of the {@code Rating}.
	 * @param submittedBy the unique device ID of the user submitting the {@code Rating}.
	 * @return A {@link ResponseEntity} with {@link HttpStatus}
	 *         {@code 201 CREATED} or {@code 400 BAD_REQUEST}/{@code 500 INTERNAL_SERVER_ERROR} if some error occurred.
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity addRating(
			@PathVariable("parking-lot-id") String parkingLotId,
			@RequestParam(value = "value") int value,
			@RequestParam(value = "submitted-by") String submittedBy) {
		LOGGER.debug("Start of RatingController.addRating()");
		ResponseEntity response = null;
		
		try {
			if (!isValueValid(value)) {
				throw new ValidationException("Unexpected Rating value provided!");
			}
			
			boolean isCreated = ratingService.addRating(new Rating(parkingLotId, value, submittedBy));
			if (isCreated) {
				response = new ResponseEntity(HttpStatus.CREATED);
			}
			else {
				throw new Exception("Rating not successfully added, please try again!");
			}
			
		} catch (ValidationException e) {
			response = handleValidationException(e.getMessage());
		} catch (Exception e) {
			response = handleUnexpectedException(e.getMessage());
		}
		
		LOGGER.debug("End of RatingController.addRating()");
		return response;
	}
	
	/**
	 * Checks if the given {@link Rating} value attribute is within the valid range.
	 */
	private boolean isValueValid(int value) {
		if(value >= MIN_RATING_VALUE && value <= MAX_RATING_VALUE) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Handles requests with no additional filters.
	 */
 	private ResponseEntity handleGetRatingsByParkingLotId(String parkingLotId) throws Exception {
		return new ResponseEntity<List<Rating>>(
				ratingService.getRatingsByParkingLot(parkingLotId), 
				HttpStatus.OK);
	}
	
	/**
	 * Handles requests with Weekday parameter present.
	 */
	private ResponseEntity handleGetRatingsByWeekday(String parkingLotId, String weekday) throws Exception {
		DayOfWeek dayEnum;
		try {
			dayEnum = DayOfWeek.valueOf(weekday);
		} catch (Exception e) {
			throw new ValidationException(String.format("Weekday value  %s  is not a valid weekday", weekday));
		}
		
		return new ResponseEntity<List<Rating>>(
				ratingService.getRatingsByDayOfWeek(parkingLotId, dayEnum), 
				HttpStatus.OK);
	}
	
	/**
	 * Handles requests with a single hour provided.
	 */
	private ResponseEntity handleGetRatingsByHour(String parkingLotId, int hour) throws Exception {
		if (!isHourRangeValid(hour)) {
			throw new Exception("Invalid hour specified!");
		}

		return new ResponseEntity<List<Rating>>(
				ratingService.getRatingsByHour(parkingLotId, hour), HttpStatus.OK);
	}
	
	/**
	 * Handles requests with a specified hour range.
	 */
	private ResponseEntity handleGetRatingsBetweenHours(String parkingLotId, int minHour, int maxHour) throws Exception {
		if(!isHourRangeValid(minHour, maxHour)) {
			throw new Exception("Invalid hour range!");
		}
		return new ResponseEntity<List<Rating>>(
				ratingService.getRatingsBetweenHours(parkingLotId, minHour, maxHour), 
				HttpStatus.OK);
	}
	
	/**
	 * Checks if at least one of the hour parameters are present.
	 * 
	 * @param minHour optional minimum hour parameter.
	 * @param maxHour optional maximum hour parameter.
	 * @return {@code true} if at least one of the parameters is present.
	 */
	private boolean isHourParamPresent(int minHour, int maxHour) {
		if (minHour == -1 && maxHour == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if both hour parameters are present.
	 * 
	 * @param minHour the required minimum hour.
	 * @param maxHour the required maximum hour.
	 * @return {@code true} if both of the parameters are present.
	 */
	private boolean isBothHourParamsPresent(int minHour, int maxHour) {
		if (minHour != -1 && maxHour != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the supplied hour range is valid for the implemented Database's
	 * hours.
	 * 
	 * @param min the minimum hour used.
	 * @param max the maximum hour used.
	 * @return {@code true} if the provided hours meet the valid min/max
	 *         range specifications.
	 */
	private boolean isHourRangeValid(int min, int max) {
		if (min > max || min <= DEFAULT_MIN_HOUR || max >= DEFAULT_MAX_HOUR) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the given hour is a searchable hour for the implemented
	 * Database hours.
	 * 
	 * @param hour the hour to check.
	 * @return {@code true} if the provided hour meets the valid min/max
	 *         range specifications.
	 */
	private boolean isHourRangeValid(int hour) {
		return DEFAULT_MIN_HOUR <= hour && hour <= DEFAULT_MAX_HOUR;
	}
	
	/**
	 * Handles responses where a {@link ValidationException} occurred.
	 * 
	 * @param message the message of the exception.
	 * @return A {@link ResponseEntity} with {@code HttpStatus} 400 BAD_REQUEST.
	 */
	private ResponseEntity handleValidationException(String message) {
		final String msg = String.format("Validation error: %s", message);
		LOGGER.error(msg);
		return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handles responses where an unexpected {@code Exception} occured.
	 * 
	 * @param message the message of the exception.
	 * @return A {@link ResponseEntity} with {@code HttpStatus} 500 INTERNAL_SERVER_ERROR.
	 */
	private ResponseEntity handleUnexpectedException(String message) {
		final String msg = String.format("Unexpected error: %s", message);
		LOGGER.error(msg);
		return new ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
