package me.apjung.backend.service.Auth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.repository.UserRepository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void register(AuthRequest.Register request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .mobile(request.getMobile())
                .build();

        userRepository.save(user);
    }
}
