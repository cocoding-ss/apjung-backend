package me.apjung.backend.dto.response;

import lombok.*;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.user.UserRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResponse {
    @Getter
    @ToString
    @AllArgsConstructor
    public static class Token {
        private final String token;
        private String type = "Bearer";

        public static Token from(String token, String type) {
            return new Token(token, type);
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Login {
        private final Token accessToken;
        private final Token refreshToken;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class TokenIssuance {
        private final Token accessToken;
    }

    @Data
    @NoArgsConstructor
    public static class Me implements Serializable {
        private String email;
        private String name;
        private boolean isEmailAuth;
        private String mobile;
        private List<String> roles = new ArrayList<>();

        @Builder
        public Me(String email, String name, boolean isEmailAuth, String mobile, List<UserRole> userRoles) {
            this.email = email;
            this.name = name;
            this.isEmailAuth = isEmailAuth;
            this.mobile = mobile;
            this.roles = userRoles.stream().map((role) -> role.getRole().getCode().toString()).collect(Collectors.toList());
        }

        public static Me create(User user) {
            return Me.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .isEmailAuth(user.isEmailAuth())
                    .mobile(user.getMobile())
                    .userRoles(user.getUserRoles())
                    .build();
        }
    }
}
