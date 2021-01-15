package me.apjung.backend.service.auth;

import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.api.exception.auth.InvalidGrantException;
import me.apjung.backend.api.exception.auth.UnsupportedGrantTypeException;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.repository.user.UserRepository;
import me.apjung.backend.service.security.jwt.AccessTokenProvider;
import me.apjung.backend.service.security.jwt.RefreshTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenProvider accessTokenProvider;
    @Mock
    private RefreshTokenProvider refreshTokenProvider;

    @Test
    @DisplayName("중복되지 않은 이메일 테스트")
    void notDuplicatedEmailTest() {
        // given
        final var email = anyString();

        given(userRepository.existsByEmail(email))
                .willReturn(false);

        // when, then
        assertDoesNotThrow(() ->
                ReflectionTestUtils.invokeMethod(authService, "emailDuplicatedCheck", email));
    }

    @Test
    @DisplayName("중복된 이메일 테스트")
    void duplicatedEmailTest() {
        // given
        final var email = anyString();

        given(userRepository.existsByEmail(email))
                .willReturn(true);

        // when, then
        assertThrows(DuplicatedEmailException.class,
                () -> ReflectionTestUtils.invokeMethod(authService, "emailDuplicatedCheck", email));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void jwtLoginTest() {
        // given
        final var accessToken = "accessToken";
        final var refreshToken = "refreshToken";
        final var request = new AuthRequest.Login();
        request.setEmail("test@test.com");
        request.setPassword("testpass");
        final var user = mock(User.class);

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));
        given(user.getPassword())
                .willReturn("testpass");
        given(passwordEncoder.matches(any(CharSequence.class), anyString()))
                .willReturn(true);
        given(refreshTokenProvider.createToken(any(User.class)))
                .willReturn(refreshToken);
        given(accessTokenProvider.createToken(any(User.class)))
                .willReturn(accessToken);
        given(userRepository.save(any(User.class)))
                .willReturn(user);

        // when
        final var result = authService.jwtLogin(request);

        // then
        verify(user, times(1))
                .login(anyString());
        assertEquals(refreshToken, result.getRefreshToken().getToken());
        assertEquals("Bearer", result.getRefreshToken().getType());
        assertEquals(accessToken, result.getAccessToken().getToken());
        assertEquals("Bearer", result.getAccessToken().getType());
    }

    @Test
    @DisplayName("로그인 실패 테스트(존재하지 않는 email)")
    void jwtLoginFailureWithEmailNotFoundTest() {
        // given
        final var request = new AuthRequest.Login();
        request.setEmail("test@test.com");
        request.setPassword("testpass");

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        // when, then
        assertThrows(UsernameNotFoundException.class, () -> authService.jwtLogin(request));
    }

    @Test
    @DisplayName("로그인 실패 테스트(비밀번호 불일치)")
    void jwtLoginFailureWithPasswordMismatchTest() {
        // given
        final var request = new AuthRequest.Login();
        request.setEmail("test@test.com");
        request.setPassword("testpass");
        final var user = mock(User.class);

        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(user));
        given(user.getPassword())
                .willReturn("testpass2");
        given(passwordEncoder.matches(any(CharSequence.class), anyString()))
                .willReturn(false);

        // when, then
        assertThrows(RuntimeException.class, () -> authService.jwtLogin(request));
    }

    @Test
    @DisplayName("accessToken 재발급 성공")
    void reissueAccessTokenTest() {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("refresh_token");
        request.setRefreshToken("validToken");
        final var user = mock(User.class);

        given(refreshTokenProvider.verifyToken(anyString()))
                .willReturn(true);
        given(refreshTokenProvider.getUserIdFromToken(anyString()))
                .willReturn(1L);
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(user.getRefreshToken())
                .willReturn("validToken");
        given(accessTokenProvider.createToken(any(User.class)))
                .willReturn("accessToken");

        // when
        final var result = authService.reissueAccessToken(request);

        // then
        assertEquals("accessToken", result.getAccessToken().getToken());
        assertEquals("Bearer", result.getAccessToken().getType());
    }

    @Test
    @DisplayName("accessToken 재발급 실패(지원하지 않는 인증 형식)")
    void reissueAccessTokenFailureWithUnsupportedGrantTypeTest() {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("strange_grant");
        request.setRefreshToken("valid");

        // when, then
        assertThrows(UnsupportedGrantTypeException.class, () -> authService.reissueAccessToken(request));
    }

    @Test
    @DisplayName("accessToken 재발급 실패(짧은 토큰 길이)")
    void reissueAccessTokenFailureWithShortTokenTest() {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("refresh_token");
        request.setRefreshToken("short");

        // when, then
        assertThrows(InvalidGrantException.class, () -> authService.reissueAccessToken(request));
    }

    @Test
    @DisplayName("accessToken 재발급 실패(유효하지 않은 토큰)")
    void reissueAccessTokenFailureWithInvalidTokenTest() {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("refresh_token");
        request.setRefreshToken("validToken");

        given(refreshTokenProvider.verifyToken(anyString()))
                .willReturn(false);

        // when, then
        assertThrows(InvalidGrantException.class, () -> authService.reissueAccessToken(request));
    }

    @Test
    @DisplayName("accessToken 재발급 실패(존재하지 않는 유저)")
    void reissueAccessTokenFailureUserNotFoundTest() {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("refresh_token");
        request.setRefreshToken("validToken");

        given(refreshTokenProvider.verifyToken(anyString()))
                .willReturn(true);
        given(refreshTokenProvider.getUserIdFromToken(anyString()))
                .willReturn(1L);
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when, then
        assertThrows(UsernameNotFoundException.class, () -> authService.reissueAccessToken(request));
    }

    @Test
    @DisplayName("accessToken 재발급 실패(DB의 refreshToken과 다를 경우)")
    void reissueAccessTokenFailureNotEqualsRefreshTokenInDbTest() {
        // given
        final var request = new AuthRequest.TokenIssuance();
        request.setGrantType("refresh_token");
        request.setRefreshToken("validToken");
        final var user = mock(User.class);

        given(refreshTokenProvider.verifyToken(anyString()))
                .willReturn(true);
        given(refreshTokenProvider.getUserIdFromToken(anyString()))
                .willReturn(1L);
        given(userRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(user.getRefreshToken())
                .willReturn("notEqualsToken");

        // when, then
        assertThrows(InvalidGrantException.class, () -> authService.reissueAccessToken(request));
    }
}
