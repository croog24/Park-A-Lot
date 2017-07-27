package com.github.parkalot.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.parkalot.model.Rating;
import com.github.parkalot.request.QueryRequest;
import com.github.parkalot.service.RatingService;

@RestController
@RequestMapping("/rating/{parking-lot-id}")
public class RatingController {

    private static final Logger LOGGER = Logger.getLogger(RatingController.class);

    private final RatingService ratingService;

    @Autowired
    public RatingController(final RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /** If both minHour and maxHour are provided, if will filter by the hour range. */
    @GetMapping
    @ResponseStatus(value = OK)
    public @ResponseBody List<Rating> getRatings(
            @PathVariable("parking-lot-id") final String parkingLotId,
            @RequestParam(value = "weekday", required = false) final String weekday,
            @RequestParam(value = "min-hour", required = false) final Integer minHour,
            @RequestParam(value = "max-hour", required = false) final Integer maxHour)
            throws Exception {
        LOGGER.info("Processing request for getRatings()");

        List<Rating> responseList;

        final QueryRequest request = new QueryRequest(parkingLotId, weekday, minHour, maxHour);

        switch (request.getQueryType()) {
            case WEEKDAY:
                responseList = ratingService.getRatingsByDayOfWeek(request);
                break;
            case HOUR:
                responseList = ratingService.getRatingsByHour(request);
                break;
            case HOUR_RANGE:
                responseList = ratingService.getRatingsBetweenHours(request);
                break;
            case PARKING_LOT_ID:
                responseList = ratingService.getRatingsByParkingLot(request);
                break;
            default:
                throw new Exception("Unable to determine Rating query type");
        }

        return responseList;
    }

    @PutMapping
    @ResponseStatus(value = CREATED)
    public void addRating(@PathVariable("parking-lot-id") final String parkingLotId,
            @RequestParam(value = "value") final Integer value,
            @RequestParam(value = "submitted-by") final String submittedBy) throws Exception {
        LOGGER.debug("Processing request for addRating()");
        final Rating r = new Rating(parkingLotId, value, submittedBy);
        final boolean isCreated = ratingService.addRating(r);
        if (!isCreated) {
            throw new Exception("Failed to add rating");
        }
    }
}
