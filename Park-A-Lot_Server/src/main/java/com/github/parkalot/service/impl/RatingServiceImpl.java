package com.github.parkalot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(RatingServiceImpl.class);

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
            ratingDao.addRating(rating);

            // Update the parent parking lot
            final String parkingLotId = rating.getParkingLotId();
            final ParkingLot parkingLot = parkingLotService.getParkingLotById(parkingLotId);
            if (parkingLot == null) {
                LOGGER.error("Unable to find ParkingLot " + parkingLotId);
                throw new Exception("Unable to find ParkingLot " + parkingLotId);
            }

            parkingLot.getRatingList().add(rating.getRatingId());

            final boolean lotUpdated = parkingLotService.updateParkingLot(parkingLot);
            if (!lotUpdated) {
                deleteRating(rating);
                throw new Exception("Unable to update the parent ParkingLot " + parkingLot);
            }
        } catch (Exception e) {
            LOGGER.error("Error adding Rating to Database: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateRating(final Rating rating) {
        try {
            ratingDao.updateRating(rating);
        } catch (Exception e) {
            LOGGER.error("Error updating Rating in Database: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteRating(final Rating rating) {
        try {
            ratingDao.deleteRating(rating);
        } catch (Exception e) {
            LOGGER.error("Error deleting Rating in Database: " + e.getMessage());
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
