package com.github.parkalot.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;

@RestController
@RequestMapping("/parking-lot/{parking-lot-id}")
public class ParkingLotController {

    private static final Logger LOGGER = Logger.getLogger(ParkingLotController.class);

    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotController(final ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    /**
     * Main PUT method to add a {@link ParkingLot} to the database.
     * 
     * @param parkingLotId the ParkingLot ID
     * @param name the name of the parking lot
     * @throws Exception if an error occurred while processing. Delegates to the
     *         {@link ExceptionHandlerController}
     */
    @PutMapping
    @ResponseStatus(value = CREATED)
    public void addParkingLot(@PathVariable("parking-lot-id") final String parkingLotId,
            @RequestParam(value = "name") final String name) throws Exception {
        LOGGER.debug("Processing request for addParkingLot()");
        final boolean isCreated =
                parkingLotService.addParkingLot(new ParkingLot(parkingLotId, name));
        if (!isCreated) {
            throw new Exception("Failed to add ParkingLot");
        }
    }

    /**
     * Main GET method for retrieving a {@link ParkingLot}.
     * 
     * @param parkingLotId The ParkingLot ID to search for
     * @return A non-null {@link ParkingLot} object
     * @throws Exception if an error occurred while processing. Delegates to the
     *         {@link ExceptionHandlerController}
     */
    @GetMapping
    @ResponseStatus(value = OK)
    public @ResponseBody ParkingLot getParkingLot(
            @PathVariable("parking-lot-id") final String parkingLotId) throws Exception {
        LOGGER.debug("Processing request for getParkingLot()");
        final ParkingLot parkingLot = parkingLotService.getParkingLotById(parkingLotId);
        if (parkingLot == null) {
            throw new Exception("ParkingLot not found");
        }

        return parkingLot;
    }

}
