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
	 * Adds a new {@code ParkingLot} to the Database.
	 * 
	 * @param parkingLot the <code>ParkingLot</code> to add.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public void addParkingLot(ParkingLot parkingLot) throws Exception;

	/**
	 * Updates a specified {@code ParkingLot} in the Database.
	 * 
	 * @param parkingLot the <code>ParkingLot</code> to update.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public void updateParkingLot(ParkingLot parkingLot) throws Exception;

	/**
	 * Retrieves a {@code ParkingLot} from the Database.
	 * 
	 * @param parkingLotId the ID of the <code>ParkingLot</code> to retrieve.
	 * @return ParkingLot the stored <code>ParkingLot</code> object.
	 * @throws Exception if an error occurred during the transaction.
	 */
	public ParkingLot getParkingLot(String parkingLotId) throws Exception;
}
