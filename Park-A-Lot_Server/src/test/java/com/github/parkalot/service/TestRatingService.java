package com.github.parkalot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.NoDocumentException;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.parkalot.ValidationException;
import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.model.Rating;
import com.github.parkalot.request.QueryRequest;
import com.github.parkalot.service.impl.RatingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestRatingService {

    private RatingService ratingService;

    @Mock
    private RatingDao mockRatingDao;
    @Mock
    private ParkingLotService mockParkingLotService;
    private List<Rating> mockList;
    private ParkingLot mockParkingLot;
    private Rating rating;

    @Before
    public void init() throws ValidationException {
        ratingService = new RatingServiceImpl(mockRatingDao, mockParkingLotService);
        mockList = Arrays.asList(new Rating("1", 2, "2"), new Rating("4", 5, "2"));
        mockParkingLot = new ParkingLot("1", "NAME");
        rating = new Rating("1", 5, "USER");
    }

    @Test
    public void testAddRating() {
        when(mockParkingLotService.getParkingLotById("1")).thenReturn(mockParkingLot);
        when(mockParkingLotService.updateParkingLot(mockParkingLot)).thenReturn(true);

        final boolean result = ratingService.addRating(rating);

        assertTrue("Expected all add/update calls to return true", result);
    }

    @Test
    public void testAddRating_NoParkingLotFound() {
        when(mockParkingLotService.getParkingLotById("1")).thenReturn(null);

        final boolean result = ratingService.addRating(rating);

        assertFalse("Expected ParkingLot get to return null, causing false result", result);
    }

    @Test
    public void testAddRating_UpdateParkingLotFailed() {
        when(mockParkingLotService.getParkingLotById("1")).thenReturn(mockParkingLot);
        when(mockParkingLotService.updateParkingLot(mockParkingLot)).thenReturn(false);

        final boolean result = ratingService.addRating(rating);

        assertFalse("Expected updateParkingLot() to fail, causing false result", result);
    }

    @Test
    public void testAddRating_DocumentConflictException() {
        doThrow(new DocumentConflictException("Some message")).when(mockParkingLotService)
                                                              .getParkingLotById("1");

        final boolean result = ratingService.addRating(rating);

        assertFalse("Expected no rating to be added", result);
    }

    @Test
    public void testUpdateRating() {
        final boolean result = ratingService.updateRating(rating);

        assertTrue("Expected to return true", result);
    }

    @Test
    public void testUpdateRating_NoDocumentException() {
        doThrow(new NoDocumentException("Some message")).when(mockRatingDao)
                                                        .updateRating(rating);

        final boolean result = ratingService.updateRating(rating);

        assertFalse("Expected no rating to be updated", result);
    }

    @Test
    public void testDeleteRating() {
        final boolean result = ratingService.deleteRating(rating);

        assertTrue("Expected to return true", result);
    }

    @Test
    public void testDeleteRating_NoDocumentException() {
        doThrow(new NoDocumentException("Some message")).when(mockRatingDao)
                                                        .deleteRating(rating);

        final boolean result = ratingService.deleteRating(rating);

        assertFalse("Expected no rating to be updated", result);
    }

    @Test
    public void testGetRatings_BetweenHours() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", null, 1, 2);
        when(mockRatingDao.getRatingsBetweenHours("123", 1, 2)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsBetweenHours(queryRequest);

        assertEquals("Unexpected result size", 2, resultList.size());
    }

    @Test
    public void testGetRatings_BetweenHours_InvalidRange() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", null, 10, 50);

        final List<Rating> resultList = ratingService.getRatingsBetweenHours(queryRequest);

        assertEquals("Unexpected result size", 0, resultList.size());
    }

    @Test
    public void testGetRatings_ByDayOfWeek() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", "FRIDAY", null, null);
        when(mockRatingDao.getRatingsByDayOfWeek("123", DayOfWeek.FRIDAY)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsByDayOfWeek(queryRequest);

        assertEquals("Unexpected result size", 2, resultList.size());
    }

    @Test
    public void testGetRatings_ByHour_Min() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", null, 1, null);
        when(mockRatingDao.getRatingsByHour("123", 1)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsByHour(queryRequest);

        assertEquals("Unexpected result size", 2, resultList.size());
    }

    @Test
    public void testGetRatings_ByHour_Max() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", null, null, 20);
        when(mockRatingDao.getRatingsByHour("123", 20)).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsByHour(queryRequest);

        assertEquals("Unexpected result size", 2, resultList.size());
    }

    @Test
    public void testGetRatings_ByHour_InvalidHour() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", null, 111, null);

        final List<Rating> resultList = ratingService.getRatingsByHour(queryRequest);

        assertEquals("Unexpected result size", 0, resultList.size());
    }

    @Test
    public void testGetRatings_ByParkingLot() throws ValidationException {
        final QueryRequest queryRequest = new QueryRequest("123", null, null, null);
        when(mockRatingDao.getRatingsByParkingLot("123")).thenReturn(mockList);

        final List<Rating> resultList = ratingService.getRatingsByParkingLot(queryRequest);

        assertEquals("Unexpected result size", 2, resultList.size());
    }

}
