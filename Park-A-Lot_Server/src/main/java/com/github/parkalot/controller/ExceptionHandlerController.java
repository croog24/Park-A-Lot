package com.github.parkalot.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.parkalot.ValidationException;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = Logger.getLogger(ExceptionHandlerController.class);

    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String internalServerError(Exception e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }

    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String validationError(ValidationException e) {
        LOGGER.error(e.getMessage());
        return e.getMessage();
    }
}
