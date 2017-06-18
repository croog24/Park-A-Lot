package com.github.parkalot.service;

import java.util.List;

import com.github.parkalot.model.Rating;
import com.github.parkalot.request.QueryRequest;

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
     * @return {@code true} if the {@code Rating} has been successfully deleted
     */
    boolean deleteRating(final Rating rating);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId and hour.
     * 
     * @return A list of {@code Rating}s with the given search parameters
     */
    List<Rating> getRatingsByHour(final QueryRequest request);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId and hour range.
     * 
     * @return A list of {@code Rating}s with the given search parameters
     */
    List<Rating> getRatingsBetweenHours(final QueryRequest request);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId and week day.
     * 
     * @return A list of {@code Rating}s with the given search parameters
     */
    List<Rating> getRatingsByDayOfWeek(final QueryRequest request);

    /**
     * Retrieves a {@code List} of {@link Rating}s in the supplied parkingLotId.
     * 
     * @return A list of {@code Rating}s with the given search parameters
     */
    List<Rating> getRatingsByParkingLot(final QueryRequest request);

}
