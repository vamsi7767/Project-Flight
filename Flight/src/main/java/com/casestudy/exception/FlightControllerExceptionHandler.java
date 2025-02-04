package com.casestudy.exception;

import com.casestudy.controller.FlightController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {FlightController.class})
public class FlightControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(FlightControllerExceptionHandler.class);

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> handleFlightNotFoundException(FlightNotFoundException exception) {
        logger.error("Flight not found:", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
