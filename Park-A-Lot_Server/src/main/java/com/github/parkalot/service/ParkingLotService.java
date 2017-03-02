package com.github.parkalot.service;

import java.util.List;

import com.github.parkalot.model.ParkingLot;

/**
 * Retrieves {@link ParkingLot}s from the database. Each object must have an
 * initialized {@link Rating} or it will not be stored in the database.
 * 
 * @author Craig
 *
 */
public interface ParkingLotService {

	/**
	 * Adds a {@link ParkingLot} to the database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to add.
	 * @return {@code true} if the @
	 */
	public boolean addParkingLot(ParkingLot parkingLot);

	public boolean updateParkingLot(ParkingLot parkingLot);

	/**
	 * Retrieves the {@link ParkingLot} by ID.
	 * 
	 * @param parkingLotId the ID of the {@link ParkingLot} to retrieve.
	 * @return ParkingLot the {@link ParkingLot} requested.
	 */
	public ParkingLot getParkingLotById(long parkingLotId);

	/**
	 * 
	 * @param parkingLotIdList
	 * @return
	 */
	public List<ParkingLot> getMultipleParkingLotsById(long[] parkingLotIdList);

}
