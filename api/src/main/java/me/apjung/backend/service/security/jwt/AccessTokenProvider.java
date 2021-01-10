package me.apjung.backend.service.security.jwt;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.property.JwtProps;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessTokenProvider extends BaseTokenProvider implements JwtTokenProvider {
    private final JwtProps jwtProps;

    @Override
    protected String getSecret() {
        return jwtProps.getAccessToken()
                .getSecret();
    }

    @Override
    protected Long getExpirationTimeMilliSec() {
        return jwtProps.getAccessToken()
                .getExpirationTimeMilliSec();
    }
}
