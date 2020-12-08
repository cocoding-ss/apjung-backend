package me.apjung.backend.repository.user;

import me.apjung.backend.AbstractDataJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends AbstractDataJpaTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("회원가입 이메일 중복 체크(중복된 이메일 입력)")
    public void existsByEmailWithDuplicatedEmailTest() {
        // TODO
//        String email = "testuser@gmail.com";
//        boolean result = userRepository.existsByEmail(email);
//        assertTrue(result);
    }

    @Test
    @DisplayName("회원가입 이메일 중복 체크(중복되지 않은 이메일 입력)")
    public void existsByEmailWithUniqueEmailTest() {
        // TODO
//        String email = "testuser999@gmail.com";
//        boolean result = userRepository.existsByEmail(email);
//        assertFalse(result);
    }
}