package com.github.parkalot.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.github.parkalot.TestContext;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.ParkingLotService;
import com.github.parkalot.service.RatingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
@EnableWebMvc
public class TestRatingController {

    private final static String PARKING_LOT_ID = "123";

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private RatingService mockRatingService;
    @MockBean
    private ParkingLotService mockParkingLotService;
    private MockMvc mockMvc;

    private List<Rating> mockRatingList = new ArrayList<Rating>();

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        mockRatingList.add(new Rating("123", 2, "4"));
    }

    @Test
    public void testGetRatings() throws Exception {
        when(mockRatingService.getRatingsByParkingLot(PARKING_LOT_ID)).thenReturn(mockRatingList);

        mockMvc.perform(get("/rating/" + PARKING_LOT_ID)).andExpect(status().isOk());

        verify(mockRatingService).getRatingsByParkingLot(PARKING_LOT_ID);
    }

    @Test
    public void testGetRatingsByWeekday() throws Exception {
        final DayOfWeek dayOfWeek = DayOfWeek.SATURDAY;

        when(mockRatingService.getRatingsByDayOfWeek(PARKING_LOT_ID, dayOfWeek))
                .thenReturn(mockRatingList);

        mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("weekday", dayOfWeek.name()))
                .andExpect(status().isOk());

        verify(mockRatingService).getRatingsByDayOfWeek(PARKING_LOT_ID, dayOfWeek);
    }

    @Test
    public void testGetRatingsByWeekday_BadWeekday() throws Exception {
        mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("weekday", "bad"))
                .andExpect(status().isBadRequest());

        verify(mockRatingService, never()).getRatingsByDayOfWeek(eq(PARKING_LOT_ID),
                any(DayOfWeek.class));
    }

    @Test
    public void testGetRatingsBetweenHours_OneParam() throws Exception {
        final int minHour = 10;

        when(mockRatingService.getRatingsByHour(PARKING_LOT_ID, minHour))
                .thenReturn(mockRatingList);

        mockMvc.perform(
                get("/rating/" + PARKING_LOT_ID).param("min-hour", String.valueOf(minHour)))
                .andExpect(status().isOk());

        verify(mockRatingService).getRatingsByHour(PARKING_LOT_ID, minHour);
    }

    @Test
    public void testGetRatingsBetweenHours_TwoParam() throws Exception {
        final int minHour = 10;
        final int maxHour = 20;

        when(mockRatingService.getRatingsBetweenHours(PARKING_LOT_ID, minHour, maxHour))
                .thenReturn(mockRatingList);

        mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("min-hour", String.valueOf(minHour))
                .param("max-hour", String.valueOf(maxHour))).andExpect(status().isOk());

        verify(mockRatingService).getRatingsBetweenHours(PARKING_LOT_ID, minHour, maxHour);
    }

    @Test
    public void testAddRating() throws Exception {
        final String userId = "USER";
        final int ratingValue = 3;
        final ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");

        when(mockRatingService.addRating(any(Rating.class))).thenReturn(true);
        when(mockParkingLotService.getParkingLotById(PARKING_LOT_ID)).thenReturn(mockParkingLot);
        when(mockParkingLotService.updateParkingLot(mockParkingLot)).thenReturn(true);

        mockMvc.perform(put("/rating/" + PARKING_LOT_ID).param("value", "" + ratingValue)
                .param("submitted-by", userId)).andExpect(status().isCreated());

        verify(mockRatingService).addRating(any(Rating.class));
        verify(mockParkingLotService).getParkingLotById(PARKING_LOT_ID);
        verify(mockParkingLotService).updateParkingLot(mockParkingLot);
        verify(mockRatingService, never()).deleteRating(any(Rating.class));
    }

    @Test
    public void testAddRating_FailedParkingLotUpdate() throws Exception {
        final String userId = "USER";
        final int ratingValue = 3;
        final ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");

        when(mockRatingService.addRating(any(Rating.class))).thenReturn(true);
        when(mockParkingLotService.getParkingLotById(PARKING_LOT_ID)).thenReturn(mockParkingLot);
        when(mockParkingLotService.updateParkingLot(mockParkingLot)).thenReturn(false);

        mockMvc.perform(put("/rating/" + PARKING_LOT_ID).param("value", "" + ratingValue)
                .param("submitted-by", userId)).andExpect(status().isInternalServerError());

        verify(mockRatingService).addRating(any(Rating.class));
        verify(mockParkingLotService).getParkingLotById(PARKING_LOT_ID);
        verify(mockParkingLotService).updateParkingLot(mockParkingLot);
        verify(mockRatingService).deleteRating(any(Rating.class));
    }

}
