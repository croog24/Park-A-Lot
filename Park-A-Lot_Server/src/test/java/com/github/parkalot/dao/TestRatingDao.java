package com.github.parkalot.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.lightcouch.View;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.model.Rating;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TestRatingDao {

	private final static String PARKING_LOT_ID = "123";

	@Autowired
	private RatingDao ratingDao;

	@Autowired
	private CouchDbClient couchDbClient;
	
	@After
	public final void tearDown() {
		reset(couchDbClient);
	}
	
	private View createBaseMockView() {
		View mockView = mock(View.class);
		when(mockView.includeDocs(Mockito.anyBoolean())).thenReturn(mockView);
		when(mockView.key(Mockito.anyString())).thenReturn(mockView);
		when(mockView.keys(Mockito.anyListOf(String.class))).thenReturn(mockView);
		when(mockView.startKey(Mockito.anyString())).thenReturn(mockView);
		when(mockView.endKey(Mockito.anyString())).thenReturn(mockView);
		when(couchDbClient.view(anyString())).thenReturn(mockView);
		return mockView;
	}

	@Test
	public final void testAddRating() throws Exception {
		Rating r = new Rating(PARKING_LOT_ID, 1, "23");

		ratingDao.addRating(r);

		verify(couchDbClient).save(r);
	}

	@Test
	public final void testUpdateRating() throws Exception {
		Rating r = new Rating("123", 1, "23");

		ratingDao.updateRating(r);

		verify(couchDbClient).update(r);
	}

	@Test
	public final void testGetRatingsBetweenHour() throws Exception {
		final int EXPECTED_SIZE = 5;
		final int MIN_HOUR = 3;
		final int MAX_HOUR = 6;
		
		List<Rating> mockResultList = new ArrayList<Rating>();
		for (int i = 0; i < 5; i++) {
			Rating rating = new Rating(PARKING_LOT_ID, 5, "123");
			mockResultList.add(rating);
		}
		
		View mockView = createBaseMockView();
		when(mockView.query(Rating.class)).thenReturn(mockResultList);
		
		List<Rating> resultList = ratingDao.getRatingsBetweenHours(PARKING_LOT_ID, MIN_HOUR, MAX_HOUR);
		
		verify(couchDbClient).view("rating/byHour");
		verify(mockView).includeDocs(true);
		verify(mockView).startKey(Arrays.asList(PARKING_LOT_ID, MIN_HOUR + ""));
		verify(mockView).endKey(Arrays.asList(PARKING_LOT_ID, MAX_HOUR + ""));
		verify(mockView).query(Rating.class);

		assertTrue("ResultList RatingsBetweenHours() should not be empty", !resultList.isEmpty());
		assertEquals("Unexpected resultList size: ", EXPECTED_SIZE, resultList.size());
	}

	@Test
	public final void testGetRatingsByHour() throws Exception {
		final int EXPECTED_SIZE = 5;
		final int EXPECTED_HOUR = 3;

		List<Rating> mockResultList = new ArrayList<Rating>();
		for (int i = 0; i < 5; i++) {
			Rating rating = new Rating(PARKING_LOT_ID, 4, "123");
			mockResultList.add(rating);
		}
		
		View mockView = createBaseMockView();
		when(mockView.query(Rating.class)).thenReturn(mockResultList);
		
		List<Rating> resultList = ratingDao.getRatingsByHour(PARKING_LOT_ID, EXPECTED_HOUR);
		
		verify(couchDbClient).view("rating/byHour");
		verify(mockView).includeDocs(true);
		verify(mockView).keys(Arrays.asList(PARKING_LOT_ID, EXPECTED_HOUR + ""));
		verify(mockView).query(Rating.class);
		
		assertTrue("ResultList RatingsByHour() should not be empty", !resultList.isEmpty());
		assertEquals("Unexpected resultList size: ", EXPECTED_SIZE, resultList.size());
	}

	@Test
	public final void testGetRatingsByDayOfWeek() throws Exception {
		final int EXPECTED_SIZE = 5;
		final DayOfWeek EXPECTED_DAY_OF_WEEK = DayOfWeek.SATURDAY;

		List<Rating> mockResultList = new ArrayList<Rating>();
		for (int i = 0; i < 5; i++) {
			Rating rating = new Rating(PARKING_LOT_ID, 4, "123");
			rating.setDayOfWeek(DayOfWeek.SATURDAY);
			mockResultList.add(rating);
		}

		View mockView = createBaseMockView();
		when(mockView.query(Rating.class)).thenReturn(mockResultList);

		List<Rating> resultList = ratingDao.getRatingsByDayOfWeek(PARKING_LOT_ID, EXPECTED_DAY_OF_WEEK);
		
		verify(couchDbClient).view("rating/byDay");
		verify(mockView).includeDocs(true);
		verify(mockView).key(Arrays.asList(PARKING_LOT_ID, EXPECTED_DAY_OF_WEEK.toString()));
		verify(mockView).query(Rating.class);

		assertTrue("ResultList RatingsByHour() should not be empty", !resultList.isEmpty());
		assertEquals("Unexpected resultList size: ", EXPECTED_SIZE, resultList.size());
	}
	
	@Test
	public final void testGetRatingsByParkingLot() throws Exception {
		final int EXPECTED_SIZE = 5;

		List<Rating> mockResultList = new ArrayList<Rating>();
		for (int i = 0; i < 5; i++) {
			Rating rating = new Rating(PARKING_LOT_ID, 4, "123");
			mockResultList.add(rating);
		}

		View mockView = createBaseMockView();
		when(mockView.query(Rating.class)).thenReturn(mockResultList);

		List<Rating> resultList = ratingDao.getRatingsByParkingLot(PARKING_LOT_ID);
		
		verify(couchDbClient).view("rating/byParkingLot");
		verify(mockView).includeDocs(true);
		verify(mockView).key(PARKING_LOT_ID);
		verify(mockView).query(Rating.class);

		assertTrue("ResultList RatingsByParkingLot() should not be empty", !resultList.isEmpty());
		assertEquals("Unexpected resultList size: ", EXPECTED_SIZE, resultList.size());
	}

}
