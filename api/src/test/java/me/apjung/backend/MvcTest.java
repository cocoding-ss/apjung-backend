package me.apjung.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.repository.User.UserRepository;
import me.apjung.backend.service.Auth.AuthServiceImpl;
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
    @Autowired private AuthServiceImpl authService;

    protected User createNewUser() {
        AuthRequest.Register request = new AuthRequest.Register(
                "labyu2020@gmail.com",
                "test1234",
                "test",
                "0101234567"
        );
        authService.register(request);

        return authService.register(request);
    }

    protected User createNewUserWithPassword(String password) {
        AuthRequest.Register request = new AuthRequest.Register(
                "labyu2020@gmail.com",
                "password",
                "admin",
                "0101234567"
        );


        return authService.register(request);
    }

    protected String getJwtAccessToken() {
        return jwtTokenProvider.createToken(this.createNewUser());
    }

    protected String getJwtAccessToken(User user) {
        return jwtTokenProvider.createToken(user);
    }
}
