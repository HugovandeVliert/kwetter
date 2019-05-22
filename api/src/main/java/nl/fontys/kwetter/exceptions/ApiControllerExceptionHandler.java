package nl.fontys.kwetter.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ApiControllerExceptionHandler {
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity handleModelNotFoundException(ModelNotFoundException e) {
        log.error("An exception has occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleModelNotFoundException(UsernameNotFoundException e) {
        log.error("An exception has occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ModelValidationException.class)
    public ResponseEntity handleModelValidationException(ModelValidationException e) {
        log.error("An exception has occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}