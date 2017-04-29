package com.github.parkalot.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.parkalot.model.ParkingLot;
import com.github.parkalot.service.ParkingLotService;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/parking-lot/{parking-lot-id}")
public class ParkingLotController {

	private static final Logger LOGGER = Logger.getLogger(ParkingLotController.class);
	
	@Autowired
	private ParkingLotService parkingLotService;
	public ParkingLotController(ParkingLotService parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity addParkingLot(
			@PathVariable("parking-lot-id") String parkingLotId,
			@RequestParam(value = "name") String name) {
		LOGGER.debug("Start of ParkingLotController.addParkingLot()");
		ResponseEntity response = null;
		try {
			boolean isCreated = parkingLotService.addParkingLot(new ParkingLot(parkingLotId, name));
			if (isCreated) {
				response = new ResponseEntity(HttpStatus.CREATED);
			}
			else {
				throw new Exception("Parking Lot not successfully added, please try again!");
			}
		} catch (Exception e) {
			response = ResponseEntityUtils.handleUnexpectedException(e.getMessage());
		}
		
		LOGGER.debug("End of ParkingLotController.addParkingLot()");
		return response;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity getParkingLot(
			@PathVariable("parking-lot-id") String parkingLotId) {
		LOGGER.debug("Start of ParkingLotController.getParkingLot()");
		ResponseEntity response = null;
		try {
			ParkingLot p = parkingLotService.getParkingLotById(parkingLotId);
			if (p == null) {
				throw new Exception("ParkingLot not found!");
			}
			
			response = new ResponseEntity<ParkingLot>(p, HttpStatus.OK);
		} catch (Exception e) {
			response = ResponseEntityUtils.handleUnexpectedException(e.getMessage());
		}
		LOGGER.debug("End of ParkingLotController.getParkingLot()");
		return response;
	}
	
}
