package me.apjung.backend.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenIssuance {
        @NotEmpty(message = "{dto.request.AuthRequest.TokenIssuance.NotEmpty.grantType}")
        private String grantType;

        @NotEmpty(message = "{dto.request.AuthRequest.TokenIssuance.NotEmpty.refreshToken}")
        private String refreshToken;
    }
}
