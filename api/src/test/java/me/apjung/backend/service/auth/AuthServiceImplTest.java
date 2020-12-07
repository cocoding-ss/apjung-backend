package me.apjung.backend.service.auth;

import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("중복되지 않은 이메일 테스트")
    public void NotDuplicatedEmailTest() {
        // given
        String email = anyString();

        given(userRepository.existsByEmail(email))
                .willReturn(false);

        // when, then
        assertDoesNotThrow(() -> {
            ReflectionTestUtils.invokeMethod(authService, "emailDuplicatedCheck", email);
        });
    }

    @Test
    @DisplayName("중복된 이메일 테스트")
    public void duplicatedEmailTest() {
        // given
        String email = anyString();

        given(userRepository.existsByEmail(email))
                .willReturn(true);

        // when, then
        assertThrows(DuplicatedEmailException.class, () -> {
            ReflectionTestUtils.invokeMethod(authService, "emailDuplicatedCheck", email);
        });
    }
}