package com.github.parkalot.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.parkalot.ValidationException;

public abstract class ResponseEntityUtil<T> {

    private static final Logger LOGGER = Logger.getLogger(ResponseEntityUtil.class);

    /**
     * Creates a {@code ResponseEntity} with information in which a {@link ValidationException}
     * occurred.
     * 
     * @param message the message of the validation exception
     * @return A {@link ResponseEntity} with {@code HttpStatus} 400
     */
    public static ResponseEntity<String> createValidationExcResponse(final String message) {
        final String msg = String.format("Validation error: %s", message);
        LOGGER.error(msg);
        return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
    }

    /**
     * Creates a {@code ResponseEntity} with information in which an {@code Exception} occurred.
     * 
     * @param message the message of the unhandled exception
     * @return a {@link ResponseEntity} with {@code HttpStatus} 500
     */
    public static ResponseEntity<String> createUnhandledExcResponse(final String message) {
        final String msg = String.format("Unexpected error: %s", message);
        LOGGER.error(msg);
        return new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
