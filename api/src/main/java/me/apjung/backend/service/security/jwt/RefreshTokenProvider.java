package me.apjung.backend.service.security.jwt;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.property.JwtProps;

@RequiredArgsConstructor
public class RefreshTokenProvider extends BaseTokenProvider implements JwtTokenProvider {
    private final JwtProps jwtProps;

    @Override
    protected String getSecret() {
        return jwtProps.getRefreshToken()
                .getSecret();
    }

    @Override
    protected Long getExpirationTimeMilliSec() {
        return jwtProps.getRefreshToken()
                .getExpirationTimeMilliSec();
    }
}
