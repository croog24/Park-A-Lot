package com.github.parkalot.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.dao.ParkingLotDao;
import com.github.parkalot.model.ParkingLot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class TestParkingLotService {

    @Autowired
    private ParkingLotService parkingLotService;

    @MockBean
    private ParkingLotDao mockParkingLotDao;
    
    @Test
    public void testAddParkingLot() throws Exception {
        final ParkingLot parkingLot = new ParkingLot("123", "name");

        final boolean result = parkingLotService.addParkingLot(parkingLot);

        assertTrue("Did not successfully add ParkingLot", result);
        verify(mockParkingLotDao).addParkingLot(parkingLot);
    }

    @Test
    public void testUpdateParkingLot() throws Exception {
        final ParkingLot parkingLot = new ParkingLot("123", "name");

        final boolean result = parkingLotService.updateParkingLot(parkingLot);

        verify(mockParkingLotDao).updateParkingLot(parkingLot);
        assertTrue("Did not successfully update ParkingLot", result);
    }

    @Test
    public void testGetParkingLot() throws Exception {
        final String parkingLotId = "123";
        final ParkingLot parkingLot = new ParkingLot(parkingLotId, "name");
        when(mockParkingLotDao.getParkingLot(parkingLotId)).thenReturn(parkingLot);

        final ParkingLot result = parkingLotService.getParkingLotById(parkingLotId);

        verify(mockParkingLotDao).getParkingLot(parkingLotId);
        assertEquals("Unexpected ParkingLot returned", parkingLot.getParkingLotId(),
                result.getParkingLotId());
    }
}
