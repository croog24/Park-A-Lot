package com.github.parkalot.service;

import java.time.DayOfWeek;
import java.util.List;

import com.github.parkalot.model.Rating;

/**
 * A service to handle all CRUD transactions that involve a {@link Rating}.
 * 
 * @author Craig
 */
public interface RatingService {

    /**
     * Adds the provided {@link Rating} to the Database.
     * 
     * @param rating the {@code Rating} to add
     * @return {@code true} if the {@code Rating} has been successfully added
     */
    boolean addRating(final Rating rating);

    /**
     * Updates the provided {@link Rating} in the Database.
     * 
     * @param rating the {@code Rating} to update.
     * @return {@code true} if the {@code Rating} has been successfully updated
     */
    boolean updateRating(final Rating rating);

    /**
     * Deletes a {@link Rating}.
     * 
     * @param rating the {@code Rating} to delete
     */
    void deleteRating(final Rating rating);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId and hour.
     * 
     * @param parkingLotId the ID of the parking lot to search
     * @param hour the hour to search with
     * @return A list of {@code Rating} with the given search parameters
     */
    List<Rating> getRatingsByHour(final String parkingLotId, final int hour);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId and hour range.
     * 
     * @param parkingLotId the ID of the parking lot to search
     * @param minHour the minimum <b>inclusive</b> hour range to search with
     * @param maxHour the maximum <b>inclusive</b> hour range to search with
     * @return A list of {@code Rating} with the given search parameters
     */
    List<Rating> getRatingsBetweenHours(final String parkingLotId, final int minHour,
            final int maxHour);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId and week day.
     * 
     * @param parkingLotId the ID of the parking lot to search
     * @param weekDay the day of the week to search with
     * @return A list of {@code Rating} with the given search parameters
     */
    List<Rating> getRatingsByDayOfWeek(final String parkingLotId, final DayOfWeek weekDay);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId.
     * 
     * @param parkingLotId the ID of the parking lot to search
     * @return A list of {@code Rating}s with the given search parameters
     */
    List<Rating> getRatingsByParkingLot(final String parkingLotId);

}
