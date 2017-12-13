package com.github.parkalot.dao.sqlite;

import java.time.DayOfWeek;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.Rating;

@Repository
public class SqliteRatingDao implements RatingDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqliteRatingDao.class);
	private final static String RATING_TABLE = "rating";

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Rating> ratingMapper;

	@Autowired
	public SqliteRatingDao(@Qualifier("sqliteTemplate") final JdbcTemplate jdbcTemplate,
			@Qualifier("sqliteRatingMapper") RowMapper<Rating> ratingMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.ratingMapper = ratingMapper;
	}

	@Override
	public void addRating(final Rating rating) {
		final String sql = "INSERT INTO " + RATING_TABLE
				+ " (rating_id, parking_lot_id, value, hour, day_of_week, submitted_by) VALUES (?, ?, ?, ?, ?, ?)";
		LOGGER.debug("SQL: {}", sql);

		final int inserted = jdbcTemplate.update(sql, new Object[] { rating.getRatingId(), rating.getParkingLotId(),
				rating.getValue(), rating.getHour(), rating.getDayOfWeek(), rating.getSubmittedByUserId() });

		LOGGER.debug("{} rows inserted", inserted);
	}

	@Override
	public List<Rating> getRatingsByHour(final String parkingLotId, final int hour) {
		final String sql = "SELECT rating_id, parking_lot_id, value, hour, day_of_week, submitted_by FROM "
				+ RATING_TABLE + " WHERE parking_lot_id = ? AND hour = ?";
		LOGGER.debug("SQL: {}", sql);

		return jdbcTemplate.query(sql, new Object[] { parkingLotId, hour }, ratingMapper);
	}

	@Override
	public List<Rating> getRatingsBetweenHours(final String parkingLotId, final int minHour, final int maxHour) {
		final String sql = "SELECT rating_id, parking_lot_id, value, hour, day_of_week, submitted_by FROM "
				+ RATING_TABLE + " WHERE parking_lot_id = ? AND hour >= ? AND hour <= ?";
		LOGGER.debug("SQL: {}", sql);

		return jdbcTemplate.query(sql, new Object[] { parkingLotId, minHour, maxHour }, ratingMapper);
	}

	@Override
	public List<Rating> getRatingsByDayOfWeek(final String parkingLotId, final DayOfWeek weekDay) {
		final String sql = "SELECT rating_id, parking_lot_id, value, hour, day_of_week, submitted_by FROM "
				+ RATING_TABLE + " WHERE parking_lot_id = ? AND day_of_week = ?";
		LOGGER.debug("SQL: {}", sql);

		return jdbcTemplate.query(sql, new Object[] { parkingLotId, weekDay }, ratingMapper);
	}

	@Override
	public List<Rating> getRatingsByParkingLot(final String parkingLotId) {
		final String sql = "SELECT rating_id, parking_lot_id, value, hour, day_of_week, submitted_by FROM "
				+ RATING_TABLE + " WHERE parking_lot_id = ?";
		LOGGER.debug("SQL: {}", sql);

		return jdbcTemplate.query(sql, new Object[] { parkingLotId }, ratingMapper);
	}

}