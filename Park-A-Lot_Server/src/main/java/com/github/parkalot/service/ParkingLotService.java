package com.github.parkalot.service;

import com.github.parkalot.model.ParkingLot;

/**
 * Handles basic CRUD operations on {@link ParkingLot}s from the database.
 * 
 * @author Craig
 */
public interface ParkingLotService {

    /**
     * Adds a {@link ParkingLot} to the database.
     * 
     * @param parkingLot the {@code ParkingLot} to add
     * @return {@code true} if the {@code ParkingLot} has been successfully added
     */
    boolean addParkingLot(final ParkingLot parkingLot);

    /**
     * Retrieves the {@link ParkingLot} by ID.
     * 
     * @param parkingLotId the ID of the {@code ParkingLot} to retrieve
     * @return ParkingLot the {@code ParkingLot} requested
     */
    ParkingLot getParkingLotById(final String parkingLotId);

}
