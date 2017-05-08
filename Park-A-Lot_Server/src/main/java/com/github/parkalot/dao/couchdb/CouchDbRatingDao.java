package com.github.parkalot.dao.couchdb;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.Rating;

/** The RatingDao with a CouchDB based implementation.
 * 
 * TODO: Consider more practical approach for storing Ratings to retrieve by
 * weeks/seasons/etc for more accurate result sets...less parking during the
 * winter would effect results in the summer.
 * 
 * @author Craig */
@Repository
public class CouchDbRatingDao implements RatingDao {

	private static final String BY_PARKING_LOT_VIEW = "rating/byParkingLot";
	private static final String BY_DAY_VIEW = "rating/byDay";
	private static final String BY_HOUR_VIEW = "rating/byHour";

	private CouchDbClient dbClient;

	@Autowired
	public CouchDbRatingDao(CouchDbClient dbClient) {
		this.dbClient = dbClient;
	}

	@Override
	public void addRating(Rating rating) {
		dbClient.save(rating);
	}

	@Override
	public void updateRating(Rating rating) {
		dbClient.update(rating);
	}
	
	@Override
	public void deleteRating(Rating rating) {
		dbClient.remove(rating);
	}
	
	@Override
	public List<Rating> getRatingsByHour(String parkingLotId, int hour) {
		return dbClient.view(BY_HOUR_VIEW)
				.includeDocs(true)
				.keys(Arrays.asList(parkingLotId, String.valueOf(hour)))
				.query(Rating.class);
	}

	@Override
	public List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour) {
		return dbClient.view(BY_HOUR_VIEW)
				.includeDocs(true)
				.startKey(Arrays.asList(parkingLotId, String.valueOf(minHour)))
				.endKey((Arrays.asList(parkingLotId, String.valueOf(maxHour))))
				.query(Rating.class);
	}

	@Override
	public List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay) {
		return dbClient.view(BY_DAY_VIEW)
				.includeDocs(true)
				.key(Arrays.asList(parkingLotId, weekDay.toString()))
				.query(Rating.class);
	}

	@Override
	public List<Rating> getRatingsByParkingLot(String parkingLotId) {
		return dbClient.view(BY_PARKING_LOT_VIEW)
				.includeDocs(true)
				.key(parkingLotId)
				.query(Rating.class);
	}

}
