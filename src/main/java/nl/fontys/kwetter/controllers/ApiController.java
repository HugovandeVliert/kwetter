package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.ModelValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ApiController {
    @ExceptionHandler({ModelNotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public String handleModelNotFoundException(ModelNotFoundException e) {
        System.out.println("An Exception has occurred: " + e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler({ModelValidationException.class})
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleModelValidationException(ModelValidationException e) {
        System.out.println("An Exception has occurred: " + e.getMessage());
        return e.getMessage();
    }
}
