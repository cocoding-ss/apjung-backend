package me.apjung.backend.mock;

import lombok.Builder;
import lombok.Getter;
import me.apjung.backend.component.randomstringbuilder.RandomStringBuilder;

import java.util.Optional;

@Getter
public class MockUser {
    private String email;
    private String password;
    private String name;
    private String mobile;

    @Builder
    public MockUser(String email, String password, String name, String mobile) {
        this.email = Optional.ofNullable(email).orElseGet(() -> RandomStringBuilder.generateAlphaNumeric(30) + "0@gmail.com");
        this.password = Optional.ofNullable(password).orElseGet(() -> "password");
        this.name = Optional.ofNullable(name).orElseGet(() -> "testName");
        this.mobile = Optional.ofNullable(mobile).orElseGet(() -> "01012341234");
    }
}
