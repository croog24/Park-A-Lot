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

	private RatingDao ratingDao;
	
	@Autowired
	public RatingServiceImpl(RatingDao ratingDao) {
		this.ratingDao = ratingDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addRating(Rating rating) {
		try {
			ratingDao.addRating(rating);
		} catch (Exception e) {
			LOGGER.error("Error adding Rating to Database: " + e);
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
			LOGGER.error("Error updating Rating in Database: " + e);
			return false;
		}

		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteRating(Rating rating) {
		try {
			ratingDao.deleteRating(rating);
		} catch (Exception e) {
			LOGGER.error("Error deleting Rating in Database: " + e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByHour(String parkingLotId, int hour) {
		List<Rating> ratingList = new ArrayList<Rating>();
		try {
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
