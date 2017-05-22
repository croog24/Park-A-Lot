package com.github.parkalot.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.parkalot.TestContext;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
@EnableWebMvc
public class TestParkingLotController {

    final static String PARKING_LOT_ID = "123";

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    @InjectMocks
    private ParkingLotController parkingLotController;

    @Mock
    private ParkingLotService mockParkingLotService;
    private MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testAddParkingLot() throws Exception {
        final ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");
        when(mockParkingLotService.addParkingLot(mockParkingLot)).thenReturn(true);

        mockMvc.perform(put("/parking-lot/" + PARKING_LOT_ID).param("name", "NAME"))
                .andExpect(status().isCreated());

        verify(mockParkingLotService).addParkingLot(mockParkingLot);
    }

    @Test
    public void getParkingLot() throws Exception {
        final ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");
        when(mockParkingLotService.getParkingLotById(PARKING_LOT_ID)).thenReturn(mockParkingLot);

        mockMvc.perform(get("/parking-lot/" + PARKING_LOT_ID)).andExpect(status().isOk());

        verify(mockParkingLotService).getParkingLotById(PARKING_LOT_ID);
    }
}
