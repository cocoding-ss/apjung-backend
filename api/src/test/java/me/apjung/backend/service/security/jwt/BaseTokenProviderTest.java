package me.apjung.backend.service.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import me.apjung.backend.domain.user.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BaseTokenProviderTest {
    @Spy
    private BaseTokenProvider baseTokenProvider;

    private static MockedStatic<Keys> mockedKeys;
    private static MockedStatic<Jwts> mockedJwts;

    @BeforeAll
    static void beforeAll() {
        mockedKeys = Mockito.mockStatic(Keys.class);
        mockedJwts = Mockito.mockStatic(Jwts.class);
    }

    @AfterAll
    static void afterAll() {
        mockedKeys.close();
        mockedJwts.close();
    }

    @Test
    @DisplayName("토큰 유효성 검사 테스트(성공)")
    void verifyTokenTest() {
        // given
        final var token = "successToken";
        final var secretKey = mock(SecretKey.class);
        final var jwtParserBuilder = mock(JwtParserBuilder.class);
        final var jwtParser = mock(JwtParser.class);
        final var jws = mock(Jws.class);

        given(baseTokenProvider.getSecret())
                .willReturn("testSecretKey");

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenReturn(secretKey);

        mockedJwts.when(Jwts::parserBuilder)
                .thenReturn(jwtParserBuilder);

        given(jwtParserBuilder.setSigningKey(secretKey))
                .willReturn(jwtParserBuilder);
        given(jwtParserBuilder.build())
                .willReturn(jwtParser);
        given(jwtParser.parseClaimsJws(anyString()))
                .willReturn(jws);

        // when
        final var result = baseTokenProvider.verifyToken(token);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("토큰 유효성 검사 테스트(실패: 유효하지 않은 SecretKey)")
    void verifyTokenFailureWithInvalidSecretKeyTest() {
        // given
        final var token = "successToken";

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenThrow(new InvalidKeyException("InvalidKeyException occurred."));

        // when
        final var result = baseTokenProvider.verifyToken(token);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("토큰 유효성 검사 테스트(실패: 유효하지 않은 Token)")
    void verifyTokenFailureWithInvalidTokenTest() {
        // given
        final var token = "failToken";
        final var secretKey = mock(SecretKey.class);
        final var jwtParserBuilder = mock(JwtParserBuilder.class);
        final var jwtParser = mock(JwtParser.class);

        given(baseTokenProvider.getSecret())
                .willReturn("testSecretKey");

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenReturn(secretKey);

        mockedJwts.when(Jwts::parserBuilder)
                .thenReturn(jwtParserBuilder);

        given(jwtParserBuilder.setSigningKey(secretKey))
                .willReturn(jwtParserBuilder);
        given(jwtParserBuilder.build())
                .willReturn(jwtParser);
        given(jwtParser.parseClaimsJws(anyString()))
                .willThrow(new JwtException("JwtException occurred."),
                        new IllegalArgumentException("IllegalArgumentException occurred."));

        // when
        final var result = baseTokenProvider.verifyToken(token);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("토큰에서 유저 id 추출(성공)")
    void getUserIdFromTokenTest() {
        // given
        final var token = "successToken";
        final var secretKey = mock(SecretKey.class);
        final var jwtParserBuilder = mock(JwtParserBuilder.class);
        final var jwtParser = mock(JwtParser.class);
        final var jws = mock(Jws.class);
        final var claims = mock(Claims.class);

        given(baseTokenProvider.getSecret())
                .willReturn("testSecretKey");

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenReturn(secretKey);

        mockedJwts.when(Jwts::parserBuilder)
                .thenReturn(jwtParserBuilder);

        given(jwtParserBuilder.setSigningKey(secretKey))
                .willReturn(jwtParserBuilder);
        given(jwtParserBuilder.build())
                .willReturn(jwtParser);
        given(jwtParser.parseClaimsJws(anyString()))
                .willReturn(jws);
        given(jws.getBody())
                .willReturn(claims);
        given(claims.getSubject())
                .willReturn("100");

        // when
        final var result = baseTokenProvider.getUserIdFromToken(token);

        // then
        assertEquals(100L, result);
    }

    @Test
    @DisplayName("토큰에서 유저 id 추출(실패: 유저 id Long.parse 실패)")
    void getUserIdFromTokenFailureWithNANTest() {
        // given
        final var token = "successToken";
        final var secretKey = mock(SecretKey.class);
        final var jwtParserBuilder = mock(JwtParserBuilder.class);
        final var jwtParser = mock(JwtParser.class);
        final var jws = mock(Jws.class);
        final var claims = mock(Claims.class);

        given(baseTokenProvider.getSecret())
                .willReturn("testSecretKey");

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenReturn(secretKey);

        mockedJwts.when(Jwts::parserBuilder)
                .thenReturn(jwtParserBuilder);

        given(jwtParserBuilder.setSigningKey(secretKey))
                .willReturn(jwtParserBuilder);
        given(jwtParserBuilder.build())
                .willReturn(jwtParser);
        given(jwtParser.parseClaimsJws(anyString()))
                .willReturn(jws);
        given(jws.getBody())
                .willReturn(claims);
        given(claims.getSubject())
                .willReturn("NAN");

        // when, then
        assertThrows(NumberFormatException.class, () -> baseTokenProvider.getUserIdFromToken(token));
    }

    @Test
    @DisplayName("토큰 생성(성공)")
    void createTokenTest() {
        // given
        final var expectedToken = "successToken";
        final var user = mock(User.class);
        final var secretKey = mock(SecretKey.class);
        final var jwtBuilder = mock(JwtBuilder.class);

        given(baseTokenProvider.getSecret())
                .willReturn("testSecretKey");

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenReturn(secretKey);

        given(baseTokenProvider.getExpirationTimeMilliSec())
                .willReturn(3_600L);

        mockedJwts.when(Jwts::builder)
                .thenReturn(jwtBuilder);

        given(user.getId())
                .willReturn(1L);
        given(jwtBuilder.setSubject(anyString()))
                .willReturn(jwtBuilder);
        given(user.getEmail())
                .willReturn("test@test.com");
        given(jwtBuilder.setAudience(anyString()))
                .willReturn(jwtBuilder);
        given(jwtBuilder.setExpiration(any(Date.class)))
                .willReturn(jwtBuilder);
        given(jwtBuilder.signWith(any(SecretKey.class)))
                .willReturn(jwtBuilder);
        given(jwtBuilder.compact())
                .willReturn(expectedToken);

        // when
        final var result = baseTokenProvider.createToken(user);

        // then
        assertEquals(expectedToken, result);
    }

    @Test
    @DisplayName("토큰 생성(실패: 올바르지 않은 키로 생성)")
    void createTokenFailureWithInvalidKeyTest() {
        // given
        final var user = mock(User.class);
        final var secretKey = mock(SecretKey.class);
        final var jwtBuilder = mock(JwtBuilder.class);

        given(baseTokenProvider.getSecret())
                .willReturn("testSecretKey");

        mockedKeys.when(() -> Keys.hmacShaKeyFor(any(byte[].class)))
                .thenReturn(secretKey);

        given(baseTokenProvider.getExpirationTimeMilliSec())
                .willReturn(3_600L);

        mockedJwts.when(Jwts::builder)
                .thenReturn(jwtBuilder);

        given(user.getId())
                .willReturn(1L);
        given(jwtBuilder.setSubject(anyString()))
                .willReturn(jwtBuilder);
        given(user.getEmail())
                .willReturn("test@test.com");
        given(jwtBuilder.setAudience(anyString()))
                .willReturn(jwtBuilder);
        given(jwtBuilder.setExpiration(any(Date.class)))
                .willReturn(jwtBuilder);
        given(jwtBuilder.signWith(any(SecretKey.class)))
                .willThrow(new InvalidKeyException("InvalidKeyException occurred."));

        // when, then
        assertThrows(InvalidKeyException.class, () -> baseTokenProvider.createToken(user));
    }
}
