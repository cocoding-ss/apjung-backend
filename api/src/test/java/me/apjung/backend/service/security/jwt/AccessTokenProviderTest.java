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
class AccessTokenProviderTest {
    @InjectMocks
    private AccessTokenProvider accessTokenProvider;
    @Mock
    private JwtProps jwtProps;

    @Test
    @DisplayName("accessToken에서 secret, expirationTimeMilliSec 조회")
    void getSecretTest() {
        final var accessToken = new JwtProps.AccessToken();
        accessToken.setSecret("testSecret");
        accessToken.setExpirationTimeMilliSec(3_600L);
        // given
        given(jwtProps.getAccessToken())
                .willReturn(accessToken);

        // when
        final var secret = accessTokenProvider.getSecret();
        final var expirationTimeMilliSec = accessTokenProvider.getExpirationTimeMilliSec();

        // then
        assertEquals(accessToken.getSecret(), secret);
        assertEquals(accessToken.getExpirationTimeMilliSec(), expirationTimeMilliSec);
    }
}
