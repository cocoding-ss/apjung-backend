package me.apjung.backend.service.security.jwt;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.property.JwtProps;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {
    private final JwtProps jwtProps;

    public String createToken(User user) {
        return JwtTokenProvider.createToken(user, jwtProps.getRefreshTokenProps());
    }

    public Long getUserIdFromToken(String token) {
        return JwtTokenProvider.getUserIdFromToken(token, jwtProps.getRefreshTokenProps());
    }

    public boolean verifyToken(String token) {
        return JwtTokenProvider.verifyToken(token, jwtProps.getRefreshTokenProps());
    }
}
