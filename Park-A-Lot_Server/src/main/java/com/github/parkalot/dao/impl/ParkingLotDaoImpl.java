package com.github.parkalot.dao.impl;

import org.apache.log4j.Logger;
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
	
	private static final Logger LOGGER = Logger.getLogger(ParkingLotDaoImpl.class);
	
	@Autowired
	private CouchDbClient couchDbClient;
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addParkingLot(ParkingLot parkingLot) throws Exception {
		LOGGER.debug("Saving ParkingLot to DB: " + parkingLot.getId());
		couchDbClient.save(parkingLot);
		LOGGER.debug("ParkingLot added");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateParkingLot(ParkingLot parkingLot) throws Exception {
		LOGGER.debug("Updating ParkingLot in DB: " + parkingLot.getId());
		couchDbClient.update(parkingLot);
		LOGGER.debug("ParkingLot updated");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParkingLot getParkingLot(String parkingLotId) throws Exception {
		LOGGER.debug("Searching for ParkingLot in DB: " + parkingLotId);
		return couchDbClient.find(ParkingLot.class, parkingLotId);
	}

}
