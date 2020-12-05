package me.apjung.backend.api.exception;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String message) {
        super(message);
    }

    public DuplicatedEmailException() {
        super();
    }

    public String toString() {
        return this.getMessage();
    }
}