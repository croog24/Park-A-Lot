package com.github.parkalot.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.parkalot.TestContext;
import com.github.parkalot.TestHelper;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;
import com.github.parkalot.service.impl.ParkingLotServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
@WebAppConfiguration
@EnableWebMvc
public class TestParkingLotController {

	@Autowired
    private WebApplicationContext ctx;
	
	@Autowired
	private ParkingLotController parkingLotController;
	
	private ParkingLotService mockParkingLotService;
	private MockMvc mockMvc;
	final String PARKING_LOT_ID = "123";
	
	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		mockParkingLotService = mock(ParkingLotServiceImpl.class);
		TestHelper.injectMock(parkingLotController, mockParkingLotService, "parkingLotService");
	}
	
	@Test
	public void testAddParkingLot() throws Exception {
		ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");
		when(mockParkingLotService.addParkingLot(mockParkingLot)).thenReturn(true);
		
		mockMvc.perform(put("/parking-lot/" + PARKING_LOT_ID).param("name", "NAME"))
			.andExpect(status().isCreated());
		
		verify(mockParkingLotService).addParkingLot(mockParkingLot);
	}
	
	@Test
	public void getParkingLot() throws Exception {
		ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");
		when(mockParkingLotService.getParkingLotById(PARKING_LOT_ID)).thenReturn(mockParkingLot);
		
		mockMvc.perform(get("/parking-lot/" + PARKING_LOT_ID))
			.andExpect(status().isOk());
		
		verify(mockParkingLotService).getParkingLotById(PARKING_LOT_ID);
	}
}
