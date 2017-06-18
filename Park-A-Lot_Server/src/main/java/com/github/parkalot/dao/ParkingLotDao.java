package com.github.parkalot.dao;

import org.lightcouch.CouchDbClient;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.NoDocumentException;

import com.github.parkalot.model.ParkingLot;

/**
 * A DAO for basic CRUD operations involving Parking Lots.
 * 
 * @author Craig
 */
public interface ParkingLotDao {

    /**
     * Adds a new {@link ParkingLot} to the Database.
     * 
     * @param parkingLot the {@link ParkingLot} to add
     * @see {@link CouchDbClient#save(Object)}
     */
    void addParkingLot(final ParkingLot parkingLot) throws DocumentConflictException;

    /**
     * Updates a specified {@link ParkingLot} in the Database.
     * 
     * @param parkingLot the {@link ParkingLot} to update
     * @see {@link CouchDbClient#update(Object)}
     */
    void updateParkingLot(final ParkingLot parkingLot) throws DocumentConflictException;

    /**
     * Retrieves a {@link ParkingLot} from the Database.
     * 
     * @param parkingLotId the ID of the {@link ParkingLot} to retrieve
     * @return ParkingLot the non-null stored {@link ParkingLot} object
     * @see {@link CouchDbClient#find(Object)}
     */
    ParkingLot getParkingLot(final String parkingLotId) throws NoDocumentException;
}
