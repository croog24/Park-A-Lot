package com.github.parkalot.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.TestHelper;
import com.github.parkalot.dao.impl.RatingDaoImpl;
import com.github.parkalot.model.Rating;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TestRatingService {

	@Autowired
	private RatingService ratingService;

	private RatingDaoImpl mockRatingDAOImpl;
	private final List<Rating> mockList = Arrays.asList(new Rating(1, 2, 3), new Rating(4, 5, 6));

	@Before
	public void init() {
		mockRatingDAOImpl = mock(RatingDaoImpl.class);
		TestHelper.injectMock(ratingService, mockRatingDAOImpl, "ratingDao");
	}

	@Test
	public void testAddRating() throws Exception {
		Rating rating = new Rating(1, 5, 3);
		boolean result = ratingService.addRating(rating);

		assertTrue("Did not successfully add Rating", result);
	}

	@Test
	public void testUpdateRating() throws Exception {
		Rating rating = new Rating(1, 5, 3);
		boolean result = ratingService.updateRating(rating);

		assertTrue("Did not successfully update Rating", result);
	}

	@Test
	public void testGetAllRatingsByParkingLotId() throws Exception {
		final int expectedListSize = 2;
		when(mockRatingDAOImpl.getAllRatingsByParkingLotId(123L)).thenReturn(mockList);

		List<Rating> resultList = ratingService.getAllRatingsByParkingLotId(123L);

		assertEquals("Unexpected result size", expectedListSize, resultList.size());
	}

	@Test
	public void testGetRatingsBetweenHours() throws Exception {
		final int expectedListSize = 2;
		when(mockRatingDAOImpl.getRatingsBetweenHours(123L, 1, 2)).thenReturn(mockList);

		List<Rating> resultList = ratingService.getRatingsBetweenHours(123L, 1, 2);

		assertEquals("Unexpected result size", expectedListSize, resultList.size());
	}
	
	@Test
	public void testGetRatingsBetweenHours_InvalidHour() throws Exception {
		final int expectedListSize = 0;
		when(mockRatingDAOImpl.getRatingsBetweenHours(123L, 122, 222)).thenReturn(mockList);

		List<Rating> resultList = ratingService.getRatingsBetweenHours(123L, 122, 222);

		assertEquals("Unexpected result size", expectedListSize, resultList.size());
	}

	@Test
	public void testGetRatingsByDayOfWeek() throws Exception {
		final int expectedListSize = 2;
		when(mockRatingDAOImpl.getRatingsByDayOfWeek(123L, DayOfWeek.FRIDAY)).thenReturn(mockList);

		List<Rating> resultList = ratingService.getRatingsByDayOfWeek(123L, DayOfWeek.FRIDAY);

		assertEquals("Unexpected result size", expectedListSize, resultList.size());
	}

	@Test
	public void testGetRatingsByHour() throws Exception {
		final int expectedListSize = 2;
		when(mockRatingDAOImpl.getRatingsByHour(123L, 1)).thenReturn(mockList);

		List<Rating> resultList = ratingService.getRatingsByHour(123L, 1);

		assertEquals("Unexpected result size", expectedListSize, resultList.size());
	}

	@Test
	public void testGetRatingsByHour_InvalidHour() throws Exception {
		final int expectedListSize = 0;
		when(mockRatingDAOImpl.getRatingsByHour(123L, 1)).thenReturn(mockList);

		List<Rating> resultList = ratingService.getRatingsByHour(123L, 55);

		assertEquals("Unexpected result size", expectedListSize, resultList.size());
	}

}