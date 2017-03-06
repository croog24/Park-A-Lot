package com.github.parkalot.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.TestHelper;
import com.github.parkalot.dao.ParkingLotDao;
import com.github.parkalot.dao.impl.ParkingLotDaoImpl;
import com.github.parkalot.model.ParkingLot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TestParkingLotService {

	@Autowired
	private ParkingLotService parkingLotService;

	private ParkingLotDao mockParkingLotDao;

	@Before
	public final void init() throws Exception {
		mockParkingLotDao = mock(ParkingLotDaoImpl.class);
		TestHelper.injectMock(parkingLotService, mockParkingLotDao, "parkingLotDao");
	}

	@Test
	public final void testAddParkingLot() throws Exception {
		ParkingLot parkingLot = new ParkingLot("123");

		boolean result = parkingLotService.addParkingLot(parkingLot);

		assertTrue("Did not successfully add ParkingLot", result);
		verify(mockParkingLotDao).addParkingLot(parkingLot);
	}

	@Test
	public final void testUpdateParkingLot() throws Exception {
		ParkingLot parkingLot = new ParkingLot("123");

		boolean result = parkingLotService.updateParkingLot(parkingLot);

		verify(mockParkingLotDao).updateParkingLot(parkingLot);
		assertTrue("Did not successfully update ParkingLot", result);
	}

	@Test
	public final void testGetParkingLot() throws Exception {
		final String PARKING_LOT_ID = "123";
		final ParkingLot PARKING_LOT = new ParkingLot(PARKING_LOT_ID);
		when(mockParkingLotDao.getParkingLot(PARKING_LOT_ID)).thenReturn(PARKING_LOT);

		ParkingLot result = parkingLotService.getParkingLotById(PARKING_LOT_ID);

		verify(mockParkingLotDao).getParkingLot(PARKING_LOT_ID);
		assertEquals("Unexpected ParkingLot returned", PARKING_LOT.getParkingLotId(), result.getParkingLotId());
	}
}
