package nl.fontys.kwetter.exceptions;

public class ExpiredVerificationTokenException extends Exception {
    public ExpiredVerificationTokenException(String message) {
        super(message);
    }
}
