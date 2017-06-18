package com.github.parkalot.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

/**
 * Entry point for all requests involving {@link Rating} retrieval/adding.
 * 
 * @author Craig
 */
@RestController
@RequestMapping("/rating/{parking-lot-id}")
public class RatingController {

    private static final Logger LOGGER = Logger.getLogger(RatingController.class);

    private final RatingService ratingService;

    @Autowired
    public RatingController(final RatingService ratingService) {
        this.ratingService = ratingService;
    }

    /**
     * Main GET request handler for {@link Rating} types. Handles several optional filters, but a
     * {@code ParkingLotId} must be provided.
     * <p>
     * <b>Note</b>: if both {@code minHour} and {@code maxHour} are provided, if will filter by the
     * hour range.
     * 
     * @param parkingLotId the mandatory parking lot ID.
     * @param weekday the optional weekday
     * @param minHour the optional min hour
     * @param maxHour the optional max hour
     * @return A {@code List} of {@link Rating}s with HttpStatus code {@code 200 OK} or
     *         {@code 400 BAD_REQUEST}/{@code 500 INTERNAL_SERVER_ERROR} if some error occurred
     * @throws Exception
     */
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
                throw new Exception("Unable to determin Rating query type");
        }

        return responseList;
    }

    /**
     * Main PUT request handler for {@link Rating} types.
     * 
     * @param parkingLotId the mandatory parking lot ID
     * @param value the value of the {@code Rating}
     * @param submittedBy the unique device ID of the user submitting the {@code Rating}
     * @return A {@link ResponseEntity} with HttpStatus code {@code 201} if successful or
     *         {@code 400}/{@code 500} if some error occurred
     * @throws Exception
     */
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
