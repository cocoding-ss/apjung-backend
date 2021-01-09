package me.apjung.backend.service.security.jwt;

import me.apjung.backend.property.JwtProps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenProviderTest {
    @InjectMocks
    private RefreshTokenProvider refreshTokenProvider;
    @Mock
    private JwtProps jwtProps;

    @Test
    @DisplayName("refreshToken에서 secret, expirationTimeMilliSec 조회")
    void getSecretTest() {
        final var refreshToken = new JwtProps.RefreshToken();
        refreshToken.setSecret("testSecret");
        refreshToken.setExpirationTimeMilliSec(3_600L);
        // given
        given(jwtProps.getRefreshToken())
                .willReturn(refreshToken);

        // when
        final var secret = refreshTokenProvider.getSecret();
        final var expirationTimeMilliSec = refreshTokenProvider.getExpirationTimeMilliSec();

        // then
        assertEquals(refreshToken.getSecret(), secret);
        assertEquals(refreshToken.getExpirationTimeMilliSec(), expirationTimeMilliSec);
    }
}