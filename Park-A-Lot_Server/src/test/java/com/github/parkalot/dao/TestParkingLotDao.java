package com.github.parkalot.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.model.ParkingLot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TestParkingLotDao {

	@Autowired
	private ParkingLotDao parkingLotDao;
	
	@Autowired
	private CouchDbClient couchDbClient;
	
	@Test
	public void testAddParkingLot() throws Exception {
		ParkingLot p = new ParkingLot("123", "name");
		
		parkingLotDao.addParkingLot(p);
		
		verify(couchDbClient).save(p);
	}
	
	@Test
	public void testUpdateParkingLot() throws Exception {
		ParkingLot p = new ParkingLot("123", "name");
		
		parkingLotDao.updateParkingLot(p);
		
		verify(couchDbClient).update(p);
	}
	
	@Test
	public void testGetParkingLot() throws Exception {
		final String PARKING_LOT_ID = "123";
		
		ParkingLot p = new ParkingLot(PARKING_LOT_ID, "name");
		when(couchDbClient.find(ParkingLot.class, PARKING_LOT_ID)).thenReturn(p);
		
		ParkingLot result = parkingLotDao.getParkingLot("123");
		
		assertEquals("Unexpected ParkingLot retrieved: ", result.getId(), PARKING_LOT_ID);
		verify(couchDbClient).find(ParkingLot.class, PARKING_LOT_ID);
	}
}
