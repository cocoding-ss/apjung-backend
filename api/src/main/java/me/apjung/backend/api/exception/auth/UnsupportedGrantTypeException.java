package me.apjung.backend.api.exception.auth;

public class UnsupportedGrantTypeException extends RuntimeException {
    public UnsupportedGrantTypeException(String message) {
        super(message);
    }

    public UnsupportedGrantTypeException() {
        super();
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
