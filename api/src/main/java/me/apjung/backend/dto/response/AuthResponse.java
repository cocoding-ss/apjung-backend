package me.apjung.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.domain.User.UserRole;

import java.util.ArrayList;
import java.util.List;

public class AuthResponse {
    @Data
    @AllArgsConstructor
    public static class Login {
        private String token;
        private String tokenType = "Bearer";
    }

    @Data
    public static class Me {
        private String email;
        private String name;

        private boolean isEmailAuth;
        private String mobile;

        @Builder
        public Me(String email, String name, boolean isEmailAuth, String mobile, List<UserRole> userRoles) {
            this.email = email;
            this.name = name;
            this.isEmailAuth = isEmailAuth;
            this.mobile = mobile;
        }

        public static Me create(User user) {
            return Me.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .isEmailAuth(user.isEmailAuth())
                    .mobile(user.getMobile())
                    .build();
        }
    }
}
