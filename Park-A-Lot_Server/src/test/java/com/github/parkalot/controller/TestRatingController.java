package com.github.parkalot.controller;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.model.Rating;
import com.github.parkalot.request.QueryRequest;
import com.github.parkalot.service.ParkingLotService;
import com.github.parkalot.service.RatingService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RatingController.class)
public class TestRatingController {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService mockRatingService;
    @MockBean
    private ParkingLotService mockParkingLotService;

    private List<Rating> mockRatingList = new ArrayList<Rating>();

    @Before
    public void init() throws Exception {
        mockRatingList.add(new Rating("123", 2, "4"));
    }

    @Test
    public void testGetRatings_ByParkingLot() throws Exception {
        when(mockRatingService.getRatingsByParkingLot(any(QueryRequest.class))).thenReturn(
                mockRatingList);

        mockMvc.perform(get("/rating/123"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    public void testGetRatings_ByWeekday() throws Exception {
        final String weekDay = "SATURDAY";

        when(mockRatingService.getRatingsByDayOfWeek(any(QueryRequest.class))).thenReturn(
                mockRatingList);

        mockMvc.perform(get("/rating/123").param("weekday", weekDay))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())));;
    }

    @Test
    public void testGetRatingsByWeekday_BadWeekday() throws Exception {
        mockMvc.perform(get("/rating/123").param("weekday", "bad"))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRatings_BetweenHours_OneParam() throws Exception {

        when(mockRatingService.getRatingsByHour(any(QueryRequest.class))).thenReturn(
                mockRatingList);

        mockMvc.perform(get("/rating/123").param("min-hour", "10"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())));;
    }

    @Test
    public void testGetRatings_BetweenHours_TwoParam() throws Exception {
        when(mockRatingService.getRatingsBetweenHours(any(QueryRequest.class))).thenReturn(
                mockRatingList);

        mockMvc.perform(get("/rating/123").param("min-hour", "10")
                                          .param("max-hour", "20"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", not(empty())));;
    }

    @Test
    public void testAddRating() throws Exception {
        final ParkingLot mockParkingLot = new ParkingLot("123", "NAME");

        when(mockRatingService.addRating(any(Rating.class))).thenReturn(true);
        when(mockParkingLotService.getParkingLotById("123")).thenReturn(mockParkingLot);

        mockMvc.perform(put("/rating/123").param("value", "3")
                                          .param("submitted-by", "USER"))
               .andExpect(status().isCreated());
    }

    @Test
    public void testAddRating_Failed() throws Exception {
        when(mockRatingService.addRating(any(Rating.class))).thenReturn(false);

        mockMvc.perform(put("/rating/123").param("value", "3")
                                          .param("submitted-by", "USER"))
               .andExpect(status().isInternalServerError());
    }

}
