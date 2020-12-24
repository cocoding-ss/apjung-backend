package me.apjung.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.user.UserRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthResponse {
    @Data
    @AllArgsConstructor
    public static class Login {
        private String token;
        private String tokenType = "Bearer";
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
