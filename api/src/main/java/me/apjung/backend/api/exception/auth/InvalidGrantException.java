package me.apjung.backend.api.exception.auth;

public class InvalidGrantException extends RuntimeException {
    public InvalidGrantException(String message) {
        super(message);
    }

    public InvalidGrantException() {
        super();
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
