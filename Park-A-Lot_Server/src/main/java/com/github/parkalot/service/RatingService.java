package com.github.parkalot.service;

import java.time.DayOfWeek;
import java.util.List;

import com.github.parkalot.model.Rating;

/**
 * A service to handle all CRUD transactions that involve a {@link Rating}.
 * 
 * @author Craig
 *
 */
public interface RatingService {

	/**
	 * Adds the provided rating to the Database.
	 * 
	 * @param rating the rating to add.
	 * @return {@code true} if the {@link Rating} has been successfully added.
	 */
	public boolean addRating(Rating rating);

	/**
	 * Updates the provided rating in the Database.
	 * 
	 * @param rating the rating to update.
	 * @return {@code true} if the {@link Rating} has been successfully updated.
	 */
	public boolean updateRating(Rating rating);

	/**
	 * Retrieves a list of Ratings in the supplied parkingLotId and hour.
	 * 
	 * @param parkingLotId the ID of the parking lot to search.
	 * @param hour the hour to search with.
	 * @return A list of {@link Rating} with the given search parameters.
	 */
	public List<Rating> getRatingsByHour(String parkingLotId, int hour);

	/**
	 * Retrieves a list of Ratings in the supplied parkingLotId and hour range.
	 * 
	 * @param parkingLotId the ID of the parking lot to search.
	 * @param minHour the minimum <b>inclusive</b> hour range to search with.
	 * @param maxHour the maximum <b>inclusive</b> hour range to search with.
	 * @return A list of {@link Rating} with the given search parameters.
	 */
	public List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour);

	/**
	 * Retrieves a list of Ratings in the supplied parkingLotId and week day.
	 * 
	 * @param parkingLotId the ID of the parking lot to search.
	 * @param weekDay the day of the week to search with.
	 * @return A list of {@link Rating} with the given search parameters.
	 */
	public List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay);

}
