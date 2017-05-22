package com.github.parkalot.dao.couchdb;

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
 */
@Repository
public class CouchDbParkingLotDao implements ParkingLotDao {

    private static final Logger LOGGER = Logger.getLogger(CouchDbParkingLotDao.class);

    private CouchDbClient couchDbClient;

    @Autowired
    public CouchDbParkingLotDao(final CouchDbClient couchDbClient) {
        super();
        this.couchDbClient = couchDbClient;
    }

    @Override
    public void addParkingLot(final ParkingLot parkingLot) {
        LOGGER.debug("Saving ParkingLot to DB: " + parkingLot.getId());
        couchDbClient.save(parkingLot);
        LOGGER.debug("ParkingLot added");
    }

    @Override
    public void updateParkingLot(final ParkingLot parkingLot) {
        LOGGER.debug("Updating ParkingLot in DB: " + parkingLot.getId());
        couchDbClient.update(parkingLot);
        LOGGER.debug("ParkingLot updated");
    }

    @Override
    public ParkingLot getParkingLot(final String parkingLotId) {
        LOGGER.debug("Searching for ParkingLot in DB: " + parkingLotId);
        return couchDbClient.find(ParkingLot.class, parkingLotId);
    }

}
