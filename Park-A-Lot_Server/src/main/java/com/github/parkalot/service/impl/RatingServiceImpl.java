package com.github.parkalot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.parkalot.dao.RatingDao;
import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.model.Rating;
import com.github.parkalot.request.QueryRequest;
import com.github.parkalot.service.ParkingLotService;
import com.github.parkalot.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingServiceImpl.class);

    private final RatingDao ratingDao;
    private final ParkingLotService parkingLotService;

    @Autowired
    public RatingServiceImpl(final RatingDao ratingDao, final ParkingLotService parkingLotService) {
        this.ratingDao = ratingDao;
        this.parkingLotService = parkingLotService;
    }

    @Override
    public boolean addRating(final Rating rating) {
        try {
        	// TODO: Check for parking lot id existance or make FK (depends on DB...sqlite is not really for this kind of thing)
            ratingDao.addRating(rating);
        } catch (Exception e) {
            LOGGER.error("Error adding Rating to Database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Rating> getRatingsByHour(final QueryRequest request) {
        List<Rating> ratingList = new ArrayList<Rating>();
        try {
            final int hour = getAvailableHour(request);
            if (!RatingValidatorUtil.isHourValid(hour)) {
                throw new Exception("Invalid hour specified");
            }
            ratingList = ratingDao.getRatingsByHour(request.getParkingLotId(), hour);
        } catch (Exception e) {
            LOGGER.error("Error retrieving resultset for getRatingsByHour(): " + e.getMessage());
        }

        return ratingList;
    }

    private int getAvailableHour(final QueryRequest request) {
        return request.getMaxHour() == null ? request.getMinHour() : request.getMaxHour();
    }

    @Override
    public List<Rating> getRatingsBetweenHours(final QueryRequest request) {
        List<Rating> ratingList = new ArrayList<Rating>();
        try {
            if (!RatingValidatorUtil.isHourRangeValid(request.getMinHour(), request.getMaxHour())) {
                throw new Exception("Invalid hour range");
            }
            ratingList = ratingDao.getRatingsBetweenHours(request.getParkingLotId(),
                    request.getMinHour(), request.getMaxHour());
        } catch (Exception e) {
            LOGGER.error(
                    "Error retrieving resultset for getRatingsBetweenHours(): " + e.getMessage());
        }
        return ratingList;
    }

    @Override
    public List<Rating> getRatingsByDayOfWeek(final QueryRequest request) {
        List<Rating> ratingList = new ArrayList<Rating>();
        ratingList =
                ratingDao.getRatingsByDayOfWeek(request.getParkingLotId(), request.getWeekday());
        return ratingList;
    }


    @Override
    public List<Rating> getRatingsByParkingLot(final QueryRequest request) {
        List<Rating> ratingList = new ArrayList<Rating>();
        ratingList = ratingDao.getRatingsByParkingLot(request.getParkingLotId());

        return ratingList;
    }

}
