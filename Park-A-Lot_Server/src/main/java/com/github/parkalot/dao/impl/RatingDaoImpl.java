package com.github.parkalot.dao.impl;

import java.time.DayOfWeek;
import java.util.List;

import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.Rating;

/**
 * The RatingDao with a CouchDB based implementation.
 * 
 * TODO: Consider more practical approach for storing Ratings to retrieve by
 * weeks/seasons/etc for more accurate result sets...less parking during the
 * winter would effect results in the summer.
 * 
 * @author Craig
 *
 */
@Repository
public class RatingDaoImpl implements RatingDao {

	private static final String BY_HOUR_VIEW = "rating/by_hour";
	//private static final String BY_DAY_OF_WEEK_VIEW = "rating/by_day";

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
		return dbClient.view(BY_HOUR_VIEW)
				.includeDocs(true)
				.key(hour)
				.query(Rating.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour) throws Exception {
		return dbClient.view(BY_HOUR_VIEW)
				.includeDocs(true)
				.startKey(minHour)
				.endKey(maxHour)
				.query(Rating.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay) throws Exception {
		/*return dbClient.view(BY_DAY_OF_WEEK_VIEW)
				.includeDocs(true)
				.key(weekDay.getValue())
				.query(Rating.class);*/
		
		// TODO: Rating object stored more specific to what we need instead of native LocalDateTime Object
		return dbClient.view(BY_HOUR_VIEW)
				.includeDocs(true)
				.query(Rating.class);
	}

}
