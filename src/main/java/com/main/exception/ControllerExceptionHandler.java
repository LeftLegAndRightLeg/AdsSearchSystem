package com.main.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gongbailiang on 9/3/16.
 */

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(AdNotFoundException.class)
    public void handleNotFound(AdNotFoundException ex) {
        logger.error("Ad not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(IdNotFoundException.class)
    public void handleBadRequest(IdNotFoundException ex) {
        logger.error("Invalid Request(Id Not Found)");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(KeyWordsNotFoundException.class)
    public void handleBadRequest(KeyWordsNotFoundException ex) {
        logger.error("Invalid Request(Keywords Not Found)");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class)
    public void handleGeneralError(Exception ex) {
        logger.error("An error occurred when processing request", ex);
    }

}
