package me.apjung.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class AuthRequest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.email}")
        private String email;

        @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.password}")
        private String password;

        @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.name}")
        private String name;

        @NotBlank(message = "{dto.request.AuthRequest.Register.NotBlank.mobile}")
        private String mobile;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        @NotEmpty
        private String email;

        @NotEmpty
        private String password;
    }
}
