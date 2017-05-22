package com.github.parkalot.service.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.Rating;
import com.github.parkalot.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger LOGGER = Logger.getLogger(RatingServiceImpl.class);

    private RatingDao ratingDao;

    @Autowired
    public RatingServiceImpl(final RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @Override
    public boolean addRating(final Rating rating) {
        try {
            ratingDao.addRating(rating);
        } catch (Exception e) {
            LOGGER.error("Error adding Rating to Database: " + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateRating(final Rating rating) {
        try {
            ratingDao.updateRating(rating);
        } catch (Exception e) {
            LOGGER.error("Error updating Rating in Database: " + e);
            return false;
        }

        return true;
    }

    @Override
    public void deleteRating(final Rating rating) {
        try {
            ratingDao.deleteRating(rating);
        } catch (Exception e) {
            LOGGER.error("Error deleting Rating in Database: " + e);
        }
    }

    @Override
    public List<Rating> getRatingsByHour(final String parkingLotId, final int hour) {
        List<Rating> ratingList = new ArrayList<Rating>();
        try {
            ratingList = ratingDao.getRatingsByHour(parkingLotId, hour);
        } catch (Exception e) {
            LOGGER.error("Error retrieving resultset for getRatingsByHour(): " + e);
        }

        return ratingList;
    }

    @Override
    public List<Rating> getRatingsBetweenHours(final String parkingLotId, final int minHour,
            final int maxHour) {
        List<Rating> ratingList = new ArrayList<Rating>();
        try {
            ratingList = ratingDao.getRatingsBetweenHours(parkingLotId, minHour, maxHour);
        } catch (Exception e) {
            LOGGER.error("Error retrieving resultset for getRatingsBetweenHours(): " + e);
        }
        return ratingList;
    }

    @Override
    public List<Rating> getRatingsByDayOfWeek(final String parkingLotId, final DayOfWeek weekDay) {
        List<Rating> ratingList = new ArrayList<Rating>();
        try {
            ratingList = ratingDao.getRatingsByDayOfWeek(parkingLotId, weekDay);
        } catch (Exception e) {
            LOGGER.error("Error retrieving resultset: ", e);
        }
        return ratingList;
    }


    @Override
    public List<Rating> getRatingsByParkingLot(final String parkingLotId) {
        List<Rating> ratingList = new ArrayList<Rating>();
        try {
            ratingList = ratingDao.getRatingsByParkingLot(parkingLotId);
        } catch (Exception e) {
            LOGGER.error("Error retrieving resultset: ", e);
        }
        return ratingList;
    }

}
