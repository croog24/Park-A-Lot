package com.github.parkalot.dao;

import com.github.parkalot.model.ParkingLot;

/**
 * A DAO for basic CRUD operations involving Parking Lots.
 * 
 * @author Craig
 *
 */
public interface ParkingLotDao {

	/**
	 * Adds a new {@link ParkingLot} to the Database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to add.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public void addParkingLot(ParkingLot parkingLot) throws Exception;

	/**
	 * Updates a specified {@link ParkingLot} in the Database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to update.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public void updateParkingLot(ParkingLot parkingLot) throws Exception;

	/**
	 * Retrieves a {@link ParkingLot} from the Database.
	 * 
	 * @param parkingLotId the ID of the {@link ParkingLot} to retrieve.
	 * @return ParkingLot the stored {@link ParkingLot} object.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public ParkingLot getParkingLot(String parkingLotId) throws Exception;
}
