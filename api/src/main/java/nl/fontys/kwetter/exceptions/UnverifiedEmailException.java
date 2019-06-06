package nl.fontys.kwetter.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UnverifiedEmailException extends AuthenticationException {
    public UnverifiedEmailException(String message) {
        super(message);
    }
}
