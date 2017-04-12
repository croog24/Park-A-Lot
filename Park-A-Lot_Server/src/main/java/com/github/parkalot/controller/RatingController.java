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

import com.github.parkalot.model.Rating;
import com.github.parkalot.service.RatingService;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("/rating/{parking-lot-id}")
public class RatingController {
	
	private static final Logger LOGGER = Logger.getLogger(RatingController.class);
	private static final int DEFAULT_MIN_HOUR = 0;
	private static final int DEFAULT_MAX_HOUR = 23;

	@Autowired
	private RatingService ratingService;
	

	/**
	 * Main GET request handler for {@link Rating} types. Handles several
	 * optional filters, but a {@code ParkingLotId} must be provided. Note: if
	 * both {@code minHour} and {@code maxHour} are provided, if will filter by
	 * the hour range.
	 * 
	 * @param parkingLotId the mandatory parking lot ID.
	 * @param weekday the optional <b>valid</b> weekday
	 * @param minHour the optional <b>valid</b> min hour.
	 * @param maxHour the optional <b>valid</b> max hour.
	 * @return A {@code List} of {@link Rating}s with {@link HttpStatus} {@code 200 OK} or
	 *         {@code 400 BAD_REQUEST} if some error occurred.
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

		} catch (IllegalArgumentException e) {
			final String weekdayCastExcMsg = String.format("Weekday value  %s  is not a valid weekday", weekday);
			LOGGER.warn(weekdayCastExcMsg);
			response = new ResponseEntity(weekdayCastExcMsg, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			final String msg = String.format("Unexpected error: %s", e.getMessage());
			LOGGER.warn(msg);
			response = new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
		}
		
		return response;
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
		return new ResponseEntity<List<Rating>>(
				ratingService.getRatingsByDayOfWeek(parkingLotId, DayOfWeek.valueOf(weekday)), 
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
	
}
