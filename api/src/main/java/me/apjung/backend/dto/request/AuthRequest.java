package me.apjung.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

public class AuthRequest {

    @Data
    @Valid
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Register {
        private String email;
        private String password;
        private String name;
        private String mobile;
    }
}
