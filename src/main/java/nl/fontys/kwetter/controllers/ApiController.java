package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApiController {
    @ExceptionHandler({ModelNotFoundException.class})
    public ResponseEntity<String> handleModelNotFoundException(Exception e) {
        System.out.println("An Exception has occurred: " + e.getMessage());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ModelValidationException.class})
    public ResponseEntity<String> handleModelValidationException(Exception e) {
        System.out.println("An Exception has occurred: " + e.getMessage());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(e.getMessage(), responseHeaders, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
