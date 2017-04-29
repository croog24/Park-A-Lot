package com.github.parkalot.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.parkalot.dao.ParkingLotDao;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

	private static final Logger LOGGER = Logger.getLogger(ParkingLotServiceImpl.class);
	
	private ParkingLotDao parkingLotDao;
	
	@Autowired
	public ParkingLotServiceImpl(ParkingLotDao parkingLotDao) {
		this.parkingLotDao = parkingLotDao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean addParkingLot(ParkingLot parkingLot) {
		try {
			parkingLotDao.addParkingLot(parkingLot);
		} catch (Exception e) {
			LOGGER.error("Error adding ParkingLot to DB: ", e);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updateParkingLot(ParkingLot parkingLot) {
		try {
			parkingLotDao.updateParkingLot(parkingLot);
		} catch (Exception e) {
			LOGGER.error("Error updating ParkingLot in DB: ", e);
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ParkingLot getParkingLotById(String parkingLotId) {
		ParkingLot p = null;
		try {
			p = parkingLotDao.getParkingLot(parkingLotId);
		} catch (Exception e) {
			LOGGER.error("Error retrieving ParkingLot from DB: ", e);
		}
		return p;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ParkingLot> getMultipleParkingLotsById(String[] parkingLotIdList) {
		// TODO Auto-generated method stub
		return null;
	}

}
