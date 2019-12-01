package com.example.demo.service;

import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = RepositoryRestExceptionHandler.class)
public class GenericExceptionHandler {
    @ExceptionHandler(Throwable.class)
    ResponseEntity handle(Exception e) {
        return new ResponseEntity("Unable to retrieve the data. Please check the url.",new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }
}