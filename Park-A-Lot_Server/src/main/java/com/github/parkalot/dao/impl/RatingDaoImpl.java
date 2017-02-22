package com.github.parkalot.dao.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.Rating;

@Repository
public class RatingDaoImpl implements RatingDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRating(Rating rating) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateRating(Rating rating) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByHour(Long parkingLotId, int hour) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsBetweenHours(Long parkingLotId, int minHour, int maxHour) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByDayOfWeek(Long parkingLotId, DayOfWeek weekDay) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getAllRatingsByParkingLotId(Long parkingLotId) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

}
