package com.github.parkalot.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.lightcouch.View;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.ValidationException;
import com.github.parkalot.dao.couchdb.CouchDbRatingDao;
import com.github.parkalot.model.Rating;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestRatingDao {

    private RatingDao ratingDao;

    @Mock
    private CouchDbClient mockCouchDbClient;
    @Mock
    private View mockView;
    private Rating rating;
    private List<Rating> ratingList;

    @Before
    public void init() throws ValidationException {
        ratingDao = new CouchDbRatingDao(mockCouchDbClient);
        rating = new Rating("123", 1, "23");
        ratingList = Arrays.asList(rating);
        initViewMockBehavior();
    }

    private void initViewMockBehavior() {
        when(mockView.includeDocs(Mockito.anyBoolean())).thenReturn(mockView);
        when(mockView.key(Mockito.anyString())).thenReturn(mockView);
        when(mockView.keys(Mockito.anyListOf(String.class))).thenReturn(mockView);
        when(mockView.startKey(Mockito.anyString())).thenReturn(mockView);
        when(mockView.endKey(Mockito.anyString())).thenReturn(mockView);
        when(mockView.query(Rating.class)).thenReturn(ratingList);
        when(mockCouchDbClient.view(anyString())).thenReturn(mockView);
    }

    @Test
    public void testAddRating_ThrowsNoException() {
        ratingDao.addRating(rating);
    }

    @Test
    public void testUpdateRating_ThrowsNoException() {
        ratingDao.updateRating(rating);
    }

    @Test
    public void testDeleteRating_ThrowsNoException() {
        ratingDao.deleteRating(rating);
    }

    @Test
    public void testGetRatingsBetweenHour_ThrowsNoException() {
        final List<Rating> resultList = ratingDao.getRatingsBetweenHours("123", 1, 2);

        assertEquals("Unexpected resultList size: ", 1, resultList.size());
    }

    @Test
    public void testGetRatingsByHour_ThrowsNoException() {
        final List<Rating> resultList = ratingDao.getRatingsByHour("123", 3);

        assertEquals("Unexpected resultList size: ", 1, resultList.size());
    }

    @Test
    public void testGetRatingsByDayOfWeek_ThrowsNoException() {
        final List<Rating> resultList = ratingDao.getRatingsByDayOfWeek("123", DayOfWeek.FRIDAY);

        assertEquals("Unexpected resultList size: ", 1, resultList.size());
    }

    @Test
    public void testGetRatingsByParkingLot_ThrowsNoException() {
        final List<Rating> resultList = ratingDao.getRatingsByParkingLot("123");

        assertEquals("Unexpected resultList size: ", 1, resultList.size());
    }

}
