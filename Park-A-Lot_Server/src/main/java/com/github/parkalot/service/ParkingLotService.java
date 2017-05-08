package com.github.parkalot.service;

import java.util.List;

import com.github.parkalot.model.ParkingLot;

/** Handles basic CRUD operations on {@link ParkingLot}s from the database.
 * 
 * @author Craig */
public interface ParkingLotService {

	/** Adds a {@link ParkingLot} to the database.
	 * 
	 * @param parkingLot the {@code ParkingLot} to add
	 * @return {@code true} if the {@code ParkingLot} has been successfully
	 *         added */
	boolean addParkingLot(ParkingLot parkingLot);

	/** Updates the {@link ParkingLot} in the database.
	 * 
	 * @param parkingLot the {@code ParkingLot} to update
	 * @return {@code true} if the {@code ParkingLot} has been successfully
	 *         updated */
	boolean updateParkingLot(ParkingLot parkingLot);

	/** Retrieves the {@link ParkingLot} by ID.
	 * 
	 * @param parkingLotId the ID of the {@code ParkingLot} to retrieve
	 * @return ParkingLot the {@code ParkingLot} requested */
	ParkingLot getParkingLotById(String parkingLotId);

	/** TODO: Possible implement for caching...will see how the rest of the
	 * design pans out. */
	List<ParkingLot> getMultipleParkingLotsById(String[] parkingLotIdList);

}
