package com.github.parkalot.dao;

import com.github.parkalot.model.ParkingLot;

/** A DAO for basic CRUD operations involving Parking Lots.
 * 
 * @author Craig */
public interface ParkingLotDao {

	/** Adds a new {@link ParkingLot} to the Database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to add */
	void addParkingLot(ParkingLot parkingLot);

	/** Updates a specified {@link ParkingLot} in the Database.
	 * 
	 * @param parkingLot the {@link ParkingLot} to update */
	void updateParkingLot(ParkingLot parkingLot);

	/** Retrieves a {@link ParkingLot} from the Database.
	 * 
	 * @param parkingLotId the ID of the {@link ParkingLot} to retrieve
	 * @return ParkingLot the stored {@link ParkingLot} object */
	ParkingLot getParkingLot(String parkingLotId);
}
