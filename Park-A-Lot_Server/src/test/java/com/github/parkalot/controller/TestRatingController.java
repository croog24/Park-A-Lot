package com.github.parkalot.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
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
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.ParkingLotService;
import com.github.parkalot.service.RatingService;
import com.github.parkalot.service.impl.ParkingLotServiceImpl;
import com.github.parkalot.service.impl.RatingServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
@WebAppConfiguration
@EnableWebMvc
public class TestRatingController {

    final static String PARKING_LOT_ID = "123";

    @Autowired
    private WebApplicationContext ctx;

    private RatingService mockRatingService;
    private ParkingLotService mockParkingLotService;

    @Autowired
    private RatingController ratingController;

    private MockMvc mockMvc;
    List<Rating> mockRatingList = new ArrayList<Rating>();

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        mockRatingService = mock(RatingServiceImpl.class);
        mockParkingLotService = mock(ParkingLotServiceImpl.class);
        TestHelper.injectMock(ratingController, mockRatingService, "ratingService");
        TestHelper.injectMock(ratingController, mockParkingLotService, "parkingLotService");

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
        final DayOfWeek WEEKDAY = DayOfWeek.SATURDAY;

        when(mockRatingService.getRatingsByDayOfWeek(PARKING_LOT_ID, WEEKDAY))
                .thenReturn(mockRatingList);

        mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("weekday", WEEKDAY.name()))
                .andExpect(status().isOk());

        verify(mockRatingService).getRatingsByDayOfWeek(PARKING_LOT_ID, WEEKDAY);
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
        final int MIN_HOUR = 10;

        when(mockRatingService.getRatingsByHour(PARKING_LOT_ID, MIN_HOUR))
                .thenReturn(mockRatingList);

        mockMvc.perform(
                get("/rating/" + PARKING_LOT_ID).param("min-hour", String.valueOf(MIN_HOUR)))
                .andExpect(status().isOk());

        verify(mockRatingService).getRatingsByHour(PARKING_LOT_ID, MIN_HOUR);
    }

    @Test
    public void testGetRatingsBetweenHours_TwoParam() throws Exception {
        final int MIN_HOUR = 10;
        final int MAX_HOUR = 20;

        when(mockRatingService.getRatingsBetweenHours(PARKING_LOT_ID, MIN_HOUR, MAX_HOUR))
                .thenReturn(mockRatingList);

        mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("min-hour", String.valueOf(MIN_HOUR))
                .param("max-hour", String.valueOf(MAX_HOUR))).andExpect(status().isOk());

        verify(mockRatingService).getRatingsBetweenHours(PARKING_LOT_ID, MIN_HOUR, MAX_HOUR);
    }

    @Test
    public void testAddRating() throws Exception {
        final String USER_ID = "USER";
        final int RATING_VALUE = 3;
        final ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");

        when(mockRatingService.addRating(any(Rating.class))).thenReturn(true);
        when(mockParkingLotService.getParkingLotById(PARKING_LOT_ID)).thenReturn(mockParkingLot);
        when(mockParkingLotService.updateParkingLot(mockParkingLot)).thenReturn(true);

        mockMvc.perform(put("/rating/" + PARKING_LOT_ID).param("value", "" + RATING_VALUE)
                .param("submitted-by", USER_ID)).andExpect(status().isCreated());

        verify(mockRatingService).addRating(any(Rating.class));
        verify(mockParkingLotService).getParkingLotById(PARKING_LOT_ID);
        verify(mockParkingLotService).updateParkingLot(mockParkingLot);
        verify(mockRatingService, never()).deleteRating(any(Rating.class));
    }

    @Test
    public void testAddRating_FailedParkingLotUpdate() throws Exception {
        final String USER_ID = "USER";
        final int RATING_VALUE = 3;
        final ParkingLot mockParkingLot = new ParkingLot(PARKING_LOT_ID, "NAME");

        when(mockRatingService.addRating(any(Rating.class))).thenReturn(true);
        when(mockParkingLotService.getParkingLotById(PARKING_LOT_ID)).thenReturn(mockParkingLot);
        when(mockParkingLotService.updateParkingLot(mockParkingLot)).thenReturn(false);

        mockMvc.perform(put("/rating/" + PARKING_LOT_ID).param("value", "" + RATING_VALUE)
                .param("submitted-by", USER_ID)).andExpect(status().isInternalServerError());

        verify(mockRatingService).addRating(any(Rating.class));
        verify(mockParkingLotService).getParkingLotById(PARKING_LOT_ID);
        verify(mockParkingLotService).updateParkingLot(mockParkingLot);
        verify(mockRatingService).deleteRating(any(Rating.class));
    }

}
