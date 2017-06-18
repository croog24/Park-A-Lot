package com.github.parkalot.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ParkingLotController.class)
public class TestParkingLotController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotService mockParkingLotService;
    private ResultActions resultActions;

    @Test
    public void testPutParkingLot() throws Exception {
        when(mockParkingLotService.addParkingLot(any(ParkingLot.class))).thenReturn(true);

        mockMvc.perform(put("/parking-lot/123").param("name", "NAME"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPutParkingLot_Fail() throws Exception {
        when(mockParkingLotService.addParkingLot(any(ParkingLot.class))).thenReturn(false);

        mockMvc.perform(put("/parking-lot/123").param("name", "NAME"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to add ParkingLot"));
    }

    @Test
    public void testPutParkingLot_MissingNameParam() throws Exception {
        mockMvc.perform(put("/parking-lot/123")).andExpect(status().isInternalServerError())
                .andExpect(content().string("Required String parameter 'name' is not present"));
    }

    @Test
    public void testGetParkingLot() throws Exception {
        final ParkingLot mockParkingLot = new ParkingLot("123", "NAME");
        when(mockParkingLotService.getParkingLotById("123")).thenReturn(mockParkingLot);

        resultActions = mockMvc.perform(get("/parking-lot/123")).andExpect(status().isOk());
        expectParkingLot(mockParkingLot);
    }

    @Test
    public void testPutParkingLot_NoParkingLotFound() throws Exception {
        when(mockParkingLotService.getParkingLotById("123")).thenReturn(null);

        mockMvc.perform(get("/parking-lot/123")).andExpect(status().isInternalServerError())
                .andExpect(content().string("ParkingLot not found"));
    }


    private void expectParkingLot(final ParkingLot expected) throws Exception {
        resultActions.andExpect(jsonPath("$.parkingLotId", is(expected.getParkingLotId())));
        resultActions.andExpect(jsonPath("$.name", is(expected.getName())));
    }
}
