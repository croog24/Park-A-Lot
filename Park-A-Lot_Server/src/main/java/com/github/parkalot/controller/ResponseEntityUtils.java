package com.github.parkalot.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.parkalot.ValidationException;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ResponseEntityUtils {
	
	private static final Logger LOGGER = Logger.getLogger(ResponseEntityUtils.class);
	
	/**
	 * Handles responses where a {@link ValidationException} occurred.
	 * 
	 * @param message the message of the exception.
	 * @return A {@link ResponseEntity} with {@code HttpStatus} 400 BAD_REQUEST.
	 */
	public static ResponseEntity handleValidationException(String message) {
		final String msg = String.format("Validation error: %s", message);
		LOGGER.error(msg);
		return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handles responses where an unexpected {@code Exception} occured.
	 * 
	 * @param message the message of the exception.
	 * @return A {@link ResponseEntity} with {@code HttpStatus} 500 INTERNAL_SERVER_ERROR.
	 */
	public static ResponseEntity handleUnexpectedException(String message) {
		final String msg = String.format("Unexpected error: %s", message);
		LOGGER.error(msg);
		return new ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
