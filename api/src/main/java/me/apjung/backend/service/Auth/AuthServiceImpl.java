package me.apjung.backend.service.Auth;

import me.apjung.backend.component.MailService.MailService;
import me.apjung.backend.component.RandomStringBuilder.RandomStringBuilder;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.repository.UserRepository.UserRepository;
import me.apjung.backend.service.Security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, MailService mailService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailService = mailService;
    }

    @Override
    public void register(AuthRequest.Register request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .mobile(request.getMobile())
                .isEmailAuth(false)
                .emailAuthToken(RandomStringBuilder.generateAlphaNumeric(60))
                .build();

        mailService.sendEmailAuth(userRepository.save(user));
    }

    @Override
    public AuthResponse.Login jwtLogin(AuthRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(""));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }

        return new AuthResponse.Login(jwtTokenProvider.createToken(user), "Bearer");
    }
}
