package edu.shmonin.userservice.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionData> handleException(EntityNotFoundException e) {
        var data = new ExceptionData(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
