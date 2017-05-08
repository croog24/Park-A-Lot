package com.github.parkalot.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.parkalot.ValidationException;

public abstract class ResponseEntityUtils<T> {
	
	private static final Logger LOGGER = Logger.getLogger(ResponseEntityUtils.class);
	
	/** Creates a {@code ResponseEntity} with information in which a
	 * {@link ValidationException} occurred.
	 * 
	 * @param message the message of the validation exception
	 * @return A {@link ResponseEntity} with {@code HttpStatus} 400 */
	public static <T> ResponseEntity<T> createValidationExcResponse(String message) {
		final String msg = String.format("Validation error: %s", message);
		LOGGER.error(msg);
		return new ResponseEntity(msg, HttpStatus.BAD_REQUEST);
	}
	
	/** Creates a {@code ResponseEntity} with information in which an
	 * {@code Exception} occurred.
	 * 
	 * @param message the message of the unhandled exception
	 * @return a {@link ResponseEntity} with {@code HttpStatus} 500 */
	public static <T> ResponseEntity<T> createUnhandledExcResponse(String message) {
		final String msg = String.format("Unexpected error: %s", message);
		LOGGER.error(msg);
		return new ResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
