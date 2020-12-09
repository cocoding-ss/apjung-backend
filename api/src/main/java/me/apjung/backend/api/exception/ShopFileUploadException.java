package me.apjung.backend.api.exception;

public class ShopFileUploadException extends RuntimeException {
    public ShopFileUploadException(String message) {
        super(message);
    }

    public ShopFileUploadException() {
        super();
    }

    public String toString() {
        return this.getMessage();
    }
}