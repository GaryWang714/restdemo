package com.rothurtech.restdemo.controller;

import com.rothurtech.restdemo.exception.ApiError;
import com.rothurtech.restdemo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> exceptionHandlerUserNotFound(Exception e) {
        ApiError error = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
