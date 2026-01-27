package fr.hugov.auth.exception;

public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message, Throwable t) {
        super(message, t);
    }
}