package com.ivan.pruebatecnica.boot.handler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * A global exception handler class for handling exceptions in the application.
 */
@ControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles the EmptyResultDataAccessException and returns a "Not found" response with a 404 status code.
     */
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultException(EmptyResultDataAccessException ex) {
        log.info(ex.toString());
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Handles general exceptions and returns an "Internal server error" response with a 500 status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        log.error(ex.toString());
        var sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        var exceptionDetails = sw.toString();
        log.error(exceptionDetails);
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}