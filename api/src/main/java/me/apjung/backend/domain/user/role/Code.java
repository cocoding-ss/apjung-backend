package me.apjung.backend.domain.user.role;

public enum Code {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;

    Code(String value) {
        this.value = value;
    }
}
