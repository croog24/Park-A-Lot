package com.github.parkalot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.dao.couchdb.CouchDbRatingDao;
import com.github.parkalot.model.Rating;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class})
public class TestRatingService {
    
    private final static String PARKING_LOT_ID = "123";

    @Autowired
    private RatingService ratingService;

    @MockBean
    private CouchDbRatingDao mockRatingDaoImpl;
    private final List<Rating> mockList =
            Arrays.asList(new Rating("1", 2, "2"), new Rating("4", 5, "2"));

    @Test
    public void testAddRating() throws Exception {
        final Rating rating = new Rating("1", 5, "2");

        final boolean result = ratingService.addRating(rating);

        verify(mockRatingDaoImpl).addRating(rating);
        assertTrue("Did not successfully add Rating", result);
    }

    @Test
    public void testUpdateRating() throws Exception {
        final Rating rating = new Rating("1", 5, "2");
        final boolean result = ratingService.updateRating(rating);

        verify(mockRatingDaoImpl).updateRating(rating);
        assertTrue("Did not successfully update Rating", result);
    }

    @Test
    public void testGetRatingsBetweenHours() throws Exception {
        final int expectedListSize = 2;
        when(mockRatingDaoImpl.getRatingsBetweenHours(PARKING_LOT_ID, 1, 2)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsBetweenHours(PARKING_LOT_ID, 1, 2);

        verify(mockRatingDaoImpl).getRatingsBetweenHours(PARKING_LOT_ID, 1, 2);
        assertEquals("Unexpected result size", expectedListSize, resultList.size());
    }

    @Test
    public void testGetRatingsByDayOfWeek() throws Exception {
        final int expectedListSize = 2;
        when(mockRatingDaoImpl.getRatingsByDayOfWeek(PARKING_LOT_ID, DayOfWeek.FRIDAY)).thenReturn(mockList);

        final List<Rating> resultList =
                ratingService.getRatingsByDayOfWeek(PARKING_LOT_ID, DayOfWeek.FRIDAY);

        verify(mockRatingDaoImpl).getRatingsByDayOfWeek(PARKING_LOT_ID, DayOfWeek.FRIDAY);
        assertEquals("Unexpected result size", expectedListSize, resultList.size());
    }

    @Test
    public void testGetRatingsByHour() throws Exception {
        final int expectedListSize = 2;
        when(mockRatingDaoImpl.getRatingsByHour(PARKING_LOT_ID, 1)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsByHour(PARKING_LOT_ID, 1);

        verify(mockRatingDaoImpl).getRatingsByHour(PARKING_LOT_ID, 1);
        assertEquals("Unexpected result size", expectedListSize, resultList.size());
    }

    @Test
    public void testGetRatingsByParkingLot() throws Exception {
        final int expectedListSize = 2;
        when(mockRatingDaoImpl.getRatingsByParkingLot(PARKING_LOT_ID)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsByParkingLot(PARKING_LOT_ID);

        verify(mockRatingDaoImpl).getRatingsByParkingLot(PARKING_LOT_ID);
        assertEquals("Unexpected result size", expectedListSize, resultList.size());
    }

}
