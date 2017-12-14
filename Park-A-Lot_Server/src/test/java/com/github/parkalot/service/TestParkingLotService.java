package com.github.parkalot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.parkalot.dao.ParkingLotDao;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.impl.ParkingLotServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestParkingLotService {

    @InjectMocks
    private ParkingLotServiceImpl parkingLotService;

    @Mock
    private ParkingLotDao mockParkingLotDao;
    private ParkingLot parkingLot;

    @Before
    public void init() {
        parkingLot = new ParkingLot("123", "NAME");
    }

    @Test
    public void testAddParkingLot() {
        final boolean result = parkingLotService.addParkingLot(parkingLot);

        assertTrue("Did not successfully add ParkingLot", result);
    }

    @Test
    public void testGetParkingLot() {
        when(mockParkingLotDao.getParkingLot(parkingLot.getParkingLotId())).thenReturn(parkingLot);

        final ParkingLot result = parkingLotService.getParkingLotById(parkingLot.getParkingLotId());

        assertEquals("Unexpected ParkingLot returned", parkingLot.getParkingLotId(),
                     result.getParkingLotId());
    }
}
