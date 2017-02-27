package com.github.parkalot.dao;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lightcouch.CouchDbClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.parkalot.TestContext;
import com.github.parkalot.model.Rating;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class })
public class TestRatingDao {

	@Autowired
	private RatingDao ratingDao;
	
	@Autowired
	private CouchDbClient couchDbClient;
	
	@Test
	public final void testAddRating() throws Exception {
		Rating r = new Rating("123", 1, 23L);
		
		ratingDao.addRating(r);
		
		verify(couchDbClient).save(r);
	}
	
	@Test
	public final void testUpdateRating() throws Exception {
		Rating r = new Rating("123", 1, 23L);
		
		ratingDao.updateRating(r);
		
		verify(couchDbClient).update(r);
	}
	
	@Test
	@Ignore("TODO")
	public final void testGetAllRatingsByParkingLotId() throws Exception {
		ArrayList<Rating> returnList = new ArrayList<Rating>();
		Rating returnRating = new Rating("123", 1, 23L);
		returnList.add(returnRating);
		
		when(couchDbClient.find(Rating.class, "123")).thenReturn(returnRating);
		
		List<Rating> testRating = ratingDao.getAllRatingsByParkingLotId(123L);
		
		
	}
}
