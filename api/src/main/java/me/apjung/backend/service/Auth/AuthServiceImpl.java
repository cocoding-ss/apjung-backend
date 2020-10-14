package me.apjung.backend.service.Auth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import me.apjung.backend.component.CustomMessageSourceResolver.CustomMessageSourceResolver;
import me.apjung.backend.component.MailHandler.CustomMailMessage;
import me.apjung.backend.component.MailHandler.MailHandler;
import me.apjung.backend.component.RandomStringBuilder.RandomStringBuilder;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.repository.UserRepository.UserRepository;
import me.apjung.backend.service.Security.JwtTokenProvider;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailHandler mailHandler;
    private final CustomMessageSourceResolver customMessageSourceResolver;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, MailHandler mailHandler, CustomMessageSourceResolver customMessageSourceResolver) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.mailHandler = mailHandler;
        this.customMessageSourceResolver = customMessageSourceResolver;
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

        userRepository.save(user);

        Context context = new Context();
        context.setVariable("userId", user.getId());
        context.setVariable("emailAuthToken", user.getEmailAuthToken());
        context.setVariable("title", customMessageSourceResolver.getBusinessMessage("template.email.email_auth.title"));
        context.setVariable("content", customMessageSourceResolver.getBusinessMessage("template.email.email_auth.content"));
        context.setVariable("authBtn", customMessageSourceResolver.getBusinessMessage("template.email.email_auth.authBtn"));
        String mailContent = mailHandler.getTemplateHtml("email_auth", context);

        CustomMailMessage customMailMessage = CustomMailMessage.builder()
                .to(user.getEmail())
                .subject(customMessageSourceResolver.getBusinessMessage("template.email.email_auth.title"))
                .text(mailContent)
                .isHtml(true)
                .build();

        try {
            mailHandler.send(customMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
