package com.github.parkalot.service;

import java.util.List;

import com.github.parkalot.model.ParkingLot;

/**
 * Handles basic CRUD operations on {@link ParkingLot}s from the database.
 * 
 * @author Craig
 *
 */
public interface ParkingLotService {

	/**
	 * Adds a {@link ParkingLot} to the database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to add.
	 * @return {@code true} if the {@link ParkingLot} has been successfully
	 *         added.
	 */
	public boolean addParkingLot(ParkingLot parkingLot);

	/**
	 * Updates the {@Link ParkingLot} in the database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to update.
	 * @return {@code true} if the {@link ParkingLot} has been successfully
	 *         updated.
	 */
	public boolean updateParkingLot(ParkingLot parkingLot);

	/**
	 * Retrieves the {@link ParkingLot} by ID.
	 * 
	 * @param parkingLotId the ID of the {@link ParkingLot} to retrieve.
	 * @return ParkingLot the {@link ParkingLot} requested.
	 */
	public ParkingLot getParkingLotById(String parkingLotId);

	/**
	 * TODO: Possible implement for caching...will see how the rest of the
	 * design pans out.
	 */
	public List<ParkingLot> getMultipleParkingLotsById(String[] parkingLotIdList);

}
