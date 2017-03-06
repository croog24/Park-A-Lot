package com.github.parkalot.dao.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.model.Rating;

/**
 * The RatingDao with a CouchDB based implementation.
 * 
 * @author Craig
 *
 */
@Repository
public class RatingDaoImpl implements RatingDao {

	@Autowired
	private CouchDbClient dbClient;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addRating(Rating rating) throws Exception {
		dbClient.save(rating);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateRating(Rating rating) throws Exception {
		dbClient.update(rating);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByHour(String parkingLotId, int hour) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay) throws Exception {
		List<Rating> ratingList = new ArrayList<Rating>();
		// TODO Auto-generated method stub
		return ratingList;
	}

}
