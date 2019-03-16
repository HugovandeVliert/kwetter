package nl.fontys.kwetter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiControllerExceptionHandler {
    @ExceptionHandler({ModelNotFoundException.class})
    public ResponseEntity handleModelNotFoundException(ModelNotFoundException e) {
        System.out.println("An Exception has occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ModelValidationException.class})
    public ResponseEntity handleModelValidationException(ModelValidationException e) {
        System.out.println("An Exception has occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}