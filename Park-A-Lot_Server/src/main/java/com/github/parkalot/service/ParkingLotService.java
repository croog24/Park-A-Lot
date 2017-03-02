package com.github.parkalot.service;

import java.util.List;

import com.github.parkalot.model.ParkingLot;

/**
 * Retrieves {@code ParkingLot}s from the database. Each object must have an
 * initialized {@code Rating} or it will not be stored in the database.
 * 
 * @author Craig
 *
 */
public interface ParkingLotService {

	/**
	 * Retrieves the {@code ParkingLot} by ID.
	 * 
	 * @param parkingLotId the ID of the <code>ParkingLot</code> to retrieve.
	 * @return ParkingLot the <code>ParkingLot</code> requested.
	 */
	public ParkingLot getParkingLotById(long parkingLotId);

	/**
	 * 
	 * @param parkingLotIdList
	 * @return
	 */
	public List<ParkingLot> getMultipleParkingLotsById(long[] parkingLotIdList);
}
