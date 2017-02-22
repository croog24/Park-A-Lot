package com.github.parkalot.dao;

import java.time.DayOfWeek;
import java.util.List;

import com.github.parkalot.model.Rating;

/**
 * A DAO for basic CRUD methods involving Ratings in the Database.
 * 
 * @author Craig
 *
 */
public interface RatingDao {

	/**
	 * Adds a {@code Rating} to the Database.
	 * 
	 * @param rating the <code>Rating</code> to add.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public void addRating(Rating rating) throws Exception;

	/**
	 * Updates a {@code Rating} in the Database.
	 * 
	 * @param rating the <code>Rating</code> to update.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public void updateRating(Rating rating) throws Exception;

	/**
	 * Retrieves a <code>List</code> of <code>Rating</code>s by hour.
	 * 
	 * @param parkingLotId the parking lot ID to search with.
	 * @param hour the hour to search within.
	 * @return a <code>List</code> of {@code Rating}s with the specified
	 *         parameters.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public List<Rating> getRatingsByHour(Long parkingLotId, int hour) throws Exception;

	/**
	 * Retrieves a <code>List</code> of <code>Rating</code>s within a range of
	 * hours.
	 * 
	 * @param parkingLotId the parking lot ID to search with.
	 * @param minHour the minimum hour range to search.
	 * @param maxHour the maximum hour range to search.
	 * @return a <code>List</code> of {@code Rating}s with the specified
	 *         parameters.
	 * @throws Exception if an error occurred during the query.
	 */
	public List<Rating> getRatingsBetweenHours(Long parkingLotId, int minHour, int maxHour) throws Exception;

	/**
	 * Retrieves a <code>List</code> of <code>Rating</code>s on a specified day
	 * of the week.
	 * 
	 * @param weekDay the day of the week to search with.
	 * @return a <code>List</code> of {@code Rating}s with the specified
	 *         parameters.
	 * @throws Exception if an error occurred during the query.
	 */
	public List<Rating> getRatingsByDayOfWeek(Long parkingLotId, DayOfWeek weekDay) throws Exception;

	/**
	 * Retrieves a full <code>List</code> of <code>Rating</code>s in a specified
	 * parking lot ID.
	 * 
	 * @param parkingLotId the parking lot ID to search with.
	 * @return a <code>List</code> of {@code Rating}s with the specified
	 *         parameters.
	 * @throws Exception if an error occurred during the query.
	 */
	public List<Rating> getAllRatingsByParkingLotId(Long parkingLotId) throws Exception;
}