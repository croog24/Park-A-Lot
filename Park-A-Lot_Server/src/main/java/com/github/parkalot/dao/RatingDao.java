package com.github.parkalot.dao;

import java.time.DayOfWeek;
import java.util.List;

import com.github.parkalot.model.Rating;

/** A DAO for basic CRUD methods involving {@link Rating}s.
 * 
 * @author Craig */
public interface RatingDao {

	/** Adds a {@link Rating} to the Database.
	 * 
	 * @param rating the {@code Rating} to add */
	void addRating(Rating rating);

	/** Updates a {@link Rating} in the Database.
	 * 
	 * @param rating the {@code Rating} to update */
	void updateRating(Rating rating);

	/** Deletes a {@link Rating} in the Database.
	 * 
	 * @param rating the {@code Rating} to delete */
	void deleteRating(Rating rating);

	/** Retrieves a {@code List } of {@link Rating}s by hour.
	 * 
	 * @param parkingLotId the parking lot ID to search with
	 * @param hour the hour to search within
	 * @return a {@code List} of {@code Rating}s with the specified parameters */
	List<Rating> getRatingsByHour(String parkingLotId, int hour);

	/** Retrieves a {@code List} of {@link Rating}s within a range of hours.
	 * 
	 * @param parkingLotId the parking lot ID to search with
	 * @param minHour the minimum hour range to search
	 * @param maxHour the maximum hour range to search
	 * @return a {@code List} of {@code Rating}s with the specified parameters */
	List<Rating> getRatingsBetweenHours(String parkingLotId, int minHour, int maxHour);

	/** Retrieves a {@code List} of {@link Rating}s on a specified day of the
	 * week.
	 * 
	 * @param parkingLotId the parking lot ID to search with
	 * @param weekDay the day of the week to search with
	 * @return a {@code List} of {@code Rating}s with the specified parameters */
	List<Rating> getRatingsByDayOfWeek(String parkingLotId, DayOfWeek weekDay);

	/** Retrieves a {@code List} of {@link Rating}s on a {@link ParkingLot}.
	 * 
	 * @param parkingLotId the parking lot ID to search with
	 * @return a {@code List} of {@code Rating}s with the specified parameters. */
	List<Rating> getRatingsByParkingLot(String parkingLotId);

}
