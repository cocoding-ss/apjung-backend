package me.apjung.backend.api.exception;

public class ShopNotFoundException extends RuntimeException {
    public ShopNotFoundException(String message) {
        super(message);
    }

    public ShopNotFoundException() {
        super();
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
