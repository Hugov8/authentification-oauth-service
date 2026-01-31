package fr.hugov.auth.exception;

//TODO pas de runtime ?
public class EncryptionException extends RuntimeException {
    public EncryptionException(String message, Throwable t) {
        super(message, t);
    }
}