package me.apjung.backend.api.exception;

public class ShopFileUploadException extends RuntimeException {
    public ShopFileUploadException(String message) {
        super(message);
    }

    public ShopFileUploadException() {
        super();
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}