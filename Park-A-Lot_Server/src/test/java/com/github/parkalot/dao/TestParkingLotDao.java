package com.github.parkalot.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.dao.couchdb.CouchDbParkingLotDao;
import com.github.parkalot.model.ParkingLot;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestParkingLotDao {

    private ParkingLotDao parkingLotDao;

    @Mock
    private CouchDbClient mockCouchDbClient;
    private ParkingLot parkingLot;

    @Before
    public void init() {
        parkingLotDao = new CouchDbParkingLotDao(mockCouchDbClient);
        parkingLot = new ParkingLot("123", "NAME");
    }

    @Test
    public void testAddParkingLot_ThrowsNoException() throws Exception {
        parkingLotDao.addParkingLot(parkingLot);
    }

    @Test
    public void testUpdateParkingLot_ThrowsNoException() throws Exception {
        parkingLotDao.updateParkingLot(parkingLot);
    }

    @Test
    public void testGetParkingLot_ThrowsNoException() throws Exception {
        when(mockCouchDbClient.find(ParkingLot.class, parkingLot.getParkingLotId())).thenReturn(
                parkingLot);

        final ParkingLot result = parkingLotDao.getParkingLot("123");

        assertEquals("Unexpected ParkingLot retrieved: ", result.getId(),
                parkingLot.getParkingLotId());
    }
}
