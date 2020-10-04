package me.apjung.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AuthResponse {
    @Data
    @AllArgsConstructor
    public static class Login {
        private String token;
        private String tokenType = "Bearer";
    }
}
