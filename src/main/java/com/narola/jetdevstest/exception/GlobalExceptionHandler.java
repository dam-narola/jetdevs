package com.narola.jetdevstest.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
//@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        System.out.println("GenericExceptionHandler: " + ex);
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

//    @ExceptionHandler(value = {AuthorizationDeniedException.class})
//    public ResponseEntity<String> handleException(AuthorizationDeniedException ex) {
//        log.error("GenericExceptionHandler:", ex);
//        return ResponseEntity.status(403).body(ex.getMessage());
//    }


}
