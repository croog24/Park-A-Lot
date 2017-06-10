package com.github.parkalot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.parkalot.ValidationException;
import com.github.parkalot.service.GooglePlacesApiService;

@Controller
public class LocationController {

    private final GooglePlacesApiService googleApiService;
    
    @Autowired
    public LocationController(final GooglePlacesApiService googleApiService) {
        this.googleApiService = googleApiService;
    }
    
    public ResponseEntity<?> setCurrentLocation(@RequestParam final Double latitude, @RequestParam final Double longitude) {
        ResponseEntity<?> response = null;
        try {
            googleApiService.setCurrentLocation(latitude, longitude);
            response = new ResponseEntity<String>(HttpStatus.OK);
        } catch (ValidationException e) {
            response = ResponseEntityUtil.createValidationExcResponse(e.getMessage());
        }
        return response;
    }
}
