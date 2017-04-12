package com.github.parkalot.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.RatingService;
import com.github.parkalot.service.impl.RatingServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
@WebAppConfiguration
@EnableWebMvc
public class TestRatingController {

	@Autowired
    private WebApplicationContext ctx;
	
	@Autowired
	private RatingService mockRatingService;
	
	@Autowired
	private RatingController ratingController;
	
	private MockMvc mockMvc;
	List<Rating> mockRatingList = new ArrayList<Rating>();
	
	@Before
	public void init() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
		mockRatingService = mock(RatingServiceImpl.class);
		TestHelper.injectMock(ratingController, mockRatingService, "ratingService");
		
		mockRatingList.add(new Rating("1", 2, "123", 4L));
	}
	
	@Test
	public void testGetRatings() throws Exception {
		final String PARKING_LOT_ID = "123";
		
		when(mockRatingService.getRatingsByParkingLot(PARKING_LOT_ID)).thenReturn(mockRatingList);
		
		mockMvc.perform(get("/rating/" + PARKING_LOT_ID))
			.andExpect(status().isOk());
		
		verify(mockRatingService).getRatingsByParkingLot(PARKING_LOT_ID);
	}
	
	@Test
	public void testGetRatingsByWeekday() throws Exception {
		final String PARKING_LOT_ID = "123";
		final DayOfWeek WEEKDAY = DayOfWeek.SATURDAY;
		
		when(mockRatingService.getRatingsByDayOfWeek(PARKING_LOT_ID, WEEKDAY)).thenReturn(mockRatingList);
		
		mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("weekday", WEEKDAY.name()))
			.andExpect(status().isOk());
		
		verify(mockRatingService).getRatingsByDayOfWeek(PARKING_LOT_ID, WEEKDAY);
	}
	
	@Test
	public void testGetRatingsByWeekday_BadWeekday() throws Exception {
		final String PARKING_LOT_ID = "123";
		
		mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("weekday", "bad"))
			.andExpect(status().isBadRequest());
		
		verify(mockRatingService, never()).getRatingsByDayOfWeek(eq(PARKING_LOT_ID), any(DayOfWeek.class));
	}
	
	@Test
	public void testGetRatingsBetweenHours_OneParam() throws Exception {
		final String PARKING_LOT_ID = "123";
		final int MIN_HOUR = 10;
		
		when(mockRatingService.getRatingsByHour(PARKING_LOT_ID, MIN_HOUR)).thenReturn(mockRatingList);
		
		mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("min-hour", String.valueOf(MIN_HOUR)))
			.andExpect(status().isOk());
		
		verify(mockRatingService).getRatingsByHour(PARKING_LOT_ID, MIN_HOUR);
	}
	
	@Test
	public void testGetRatingsBetweenHours_TwoParam() throws Exception {
		final String PARKING_LOT_ID = "123";
		final int MIN_HOUR = 10;
		final int MAX_HOUR = 20;
		
		when(mockRatingService.getRatingsBetweenHours(PARKING_LOT_ID, MIN_HOUR, MAX_HOUR)).thenReturn(mockRatingList);
		
		mockMvc.perform(get("/rating/" + PARKING_LOT_ID).param("min-hour", String.valueOf(MIN_HOUR)).param("max-hour", String.valueOf(MAX_HOUR)))
			.andExpect(status().isOk());
		
		verify(mockRatingService).getRatingsBetweenHours(PARKING_LOT_ID, MIN_HOUR, MAX_HOUR);
	}
	
}