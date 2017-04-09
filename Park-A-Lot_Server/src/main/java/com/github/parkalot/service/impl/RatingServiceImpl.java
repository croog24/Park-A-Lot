package com.github.parkalot.service.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	private static final Logger LOGGER = Logger.getLogger(RatingServiceImpl.class);
	private static final int MIN_HOUR = 0;
	private static final int MAX_HOUR = 24;

	@Autowired
	private RatingDao ratingDao;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addRating(Rating rating) {
		try {
			ratingDao.addRating(rating);
		} catch (Exception e) {
			LOGGER.error("Error adding Rating to Database: ", e);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateRating(Rating rating) {
		try {
			ratingDao.updateRating(rating);
		} catch (Exception e) {
			LOGGER.error("Error updating Rating in Database: ", e);
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByHour(String parkingLotId, int hour) {
		List<Rating> ratingList = new ArrayList<Rating>();
		try {
			if (!isHourRangeValid(hour)) {
				throw new Exception("Invalid hour specified - must be between 0 and 24");
			}
			ratingList = ratingDao.getRatingsByHour(parkingLotId, hour);
		} catch (Exception e) {
			LOGGER.error("Error retrieving resultset for getRatingsByHour(): " + e);
		}

		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour) {
		List<Rating> ratingList = new ArrayList<Rating>();
		try {
			if (!isHourRangeValid(minHour, maxHour)) {
				throw new Exception("Invalid hour range - must be between 0 and 24");
			}

			ratingList = ratingDao.getRatingsBetweenHours(parkingLotId, minHour, maxHour);
		} catch (Exception e) {
			LOGGER.error("Error retrieving resultset for getRatingsBetweenHours(): " + e);
		}
		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay) {
		List<Rating> ratingList = new ArrayList<Rating>();
		try {
			ratingList = ratingDao.getRatingsByDayOfWeek(parkingLotId, weekDay);
		} catch (Exception e) {
			LOGGER.error("Error retrieving resultset: ", e);
		}
		return ratingList;
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
		if (min > max || min <= MIN_HOUR || max >= MAX_HOUR) {
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
		return MIN_HOUR <= hour && hour <= MAX_HOUR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByParkingLot(String parkingLotId) {
		List<Rating> ratingList = new ArrayList<Rating>();
		try {
			ratingList = ratingDao.getRatingsByParkingLot(parkingLotId);
		} catch (Exception e) {
			LOGGER.error("Error retrieving resultset: ", e);
		}
		return ratingList;
	}

}
