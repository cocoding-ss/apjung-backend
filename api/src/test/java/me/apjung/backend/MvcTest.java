package me.apjung.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.repository.UserRepository.UserRepository;
import me.apjung.backend.service.Security.JwtTokenProvider;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Disabled
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.apjung.xyz")
public abstract class MvcTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected UserRepository userRepository;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private PasswordEncoder passwordEncoder;

    protected User createNewUser() {
        User user = User.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("test1234"))
                .name("test")
                .mobile("01012345678")
                .build();

        return userRepository.save(user);
    }

    protected User createNewUserWithPassword(String password) {
        User user = User.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode(password))
                .name("test")
                .mobile("01012345678")
                .build();

        return userRepository.save(user);
    }

    protected String getJwtAccessToken(User user) {
        return jwtTokenProvider.createToken(user);
    }
}
