package com.github.parkalot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.parkalot.dao.ParkingLotDao;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParkingLotServiceImpl.class);

    private final ParkingLotDao parkingLotDao;

    @Autowired
    public ParkingLotServiceImpl(final ParkingLotDao parkingLotDao) {
        this.parkingLotDao = parkingLotDao;
    }

    @Override
    public boolean addParkingLot(final ParkingLot parkingLot) {
        try {
            parkingLotDao.addParkingLot(parkingLot);
        } catch (Exception e) {
            LOGGER.error("Error adding ParkingLot to DB: " + e);
            return false;
        }
        return true;
    }

    @Override
    public ParkingLot getParkingLotById(final String parkingLotId) {
        ParkingLot parkingLot = null;
        try {
            parkingLot = parkingLotDao.getParkingLot(parkingLotId);
        } catch (Exception e) {
            LOGGER.error("Error retrieving ParkingLot from DB: " + e);
        }
        return parkingLot;
    }

}
