package com.github.parkalot.dao;

import java.time.DayOfWeek;
import java.util.List;

import com.github.parkalot.model.Rating;

/**
 * A DAO for basic CRUD methods involving {@link Rating}s.
 * 
 * @author Craig
 *
 */
public interface RatingDao {

	/**
	 * Adds a {@link Rating} to the Database.
	 * 
	 * @param rating the {@code Rating} to add.
	 * @throws Exception if an error occurred during the transaction.
	 */
	void addRating(Rating rating) throws Exception;

	/**
	 * Updates a {@link Rating} in the Database.
	 * 
	 * @param rating the {@code Rating} to update.
	 * @throws Exception if an error occurred during the transaction.
	 */
	void updateRating(Rating rating) throws Exception;

	/**
	 * Deletes a {@link Rating} in the Database.
	 * @param rating the {@code Rating} to delete.
	 * @throws Exception if an error occurred during the transaction.
	 */
	void deleteRating(Rating rating) throws Exception;
	
	/**
	 * Retrieves a {@code List } of {@link Rating}s by hour.
	 * 
	 * @param parkingLotId the parking lot ID to search with.
	 * @param hour the hour to search within.
	 * @return a {@code List} of {@code Rating}s with the specified parameters.
	 * @throws Exception if an error occurred during the transaction.
	 */
	List<Rating> getRatingsByHour(String parkingLotId, int hour) throws Exception;

	/**
	 * Retrieves a {@code List} of {@link Rating}s within a range of hours.
	 * 
	 * @param parkingLotId the parking lot ID to search with.
	 * @param minHour the minimum hour range to search.
	 * @param maxHour the maximum hour range to search.
	 * @return a {@code List} of {@code Rating}s with the specified parameters.
	 * @throws Exception if an error occurred during the query.
	 */
	List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour) throws Exception;

	/**
	 * Retrieves a {@code List} of {@link Rating}s on a specified day of the
	 * week.
	 * 
	 * @param weekDay the day of the week to search with.
	 * @return a {@code List} of {@code Rating}s with the specified parameters.
	 * @throws Exception if an error occurred during the query.
	 */
	List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay) throws Exception;
	
	/**
	 * Retrieves a {@code List} of {@link Rating}s on a {@link ParkingLot}.
	 * 
	 * @param parkingLotId the parking lot ID to search with.
	 * @return a {@code List} of {@code Rating}s with the specified parameters.
	 * @throws Exception if an error occurred during the query.
	 */
	List<Rating> getRatingsByParkingLot(String parkingLotId) throws Exception;

}
