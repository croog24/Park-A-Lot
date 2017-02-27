package com.github.parkalot.dao.impl;

import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.parkalot.dao.ParkingLotDao;
import com.github.parkalot.model.ParkingLot;

/**
 * The ParkingLotDao with a CouchDB based implementation.
 * 
 * @author Craig
 *
 */
@Repository
public class ParkingLotDaoImpl implements ParkingLotDao {
	
	@Autowired
	private CouchDbClient couchDbClient;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParkingLot(ParkingLot parkingLot) throws Exception {
		couchDbClient.save(parkingLot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateParkingLot(ParkingLot parkingLot) throws Exception {
		couchDbClient.update(parkingLot);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParkingLot getParkingLot(String parkingLotId) throws Exception {
		ParkingLot parkingLot = couchDbClient.find(ParkingLot.class, parkingLotId);
		return parkingLot;
	}

}
