package me.apjung.backend.service.auth;

import lombok.AllArgsConstructor;
import me.apjung.backend.api.exception.DuplicatedEmailException;
import me.apjung.backend.component.mailservice.MailService;
import me.apjung.backend.component.randomstringbuilder.RandomStringBuilder;
import me.apjung.backend.domain.user.role.Code;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.user.UserRole;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.repository.role.RoleRepotisory;
import me.apjung.backend.repository.user.UserRepository;
import me.apjung.backend.repository.userrole.UserRoleRepository;
import me.apjung.backend.service.security.CustomUserDetails;
import me.apjung.backend.service.security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepotisory roleRepotisory;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;

    @Override
    @Transactional
    public User register(AuthRequest.Register request) {
        emailDuplicatedCheck(request.getEmail());

        User user = userRepository.save(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .mobile(request.getMobile())
                .isEmailAuth(false)
                .emailAuthToken(Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow())
                .build());

        UserRole userRole = UserRole.create(roleRepotisory.findRoleByCode(Code.USER).orElseThrow());
        user.addUserRoles(userRole);
        userRoleRepository.save(userRole);
        mailService.sendEmailAuth(user);

        return user;
    }

    private void emailDuplicatedCheck(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    @Override
    public User emailVerify(Long userId, String emailAuthToken) {
        User user = userRepository.findById(userId).orElseThrow();

        if (user.getEmailAuthToken().equals(emailAuthToken)) {
            user.setEmailAuth(true);
        }

        user.setEmailAuthToken(Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow());
        return userRepository.save(user);
    }

    @Override
    public AuthResponse.Login jwtLogin(AuthRequest.Login request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException(""));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }

        return new AuthResponse.Login(jwtTokenProvider.createToken(user), "Bearer");
    }

    @Override
    public AuthResponse.Me me(CustomUserDetails customUserDetails) {
        return AuthResponse.Me.create(customUserDetails.getUser());
    }
}
