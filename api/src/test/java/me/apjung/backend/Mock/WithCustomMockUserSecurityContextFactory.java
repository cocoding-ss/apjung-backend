package me.apjung.backend.Mock;

import me.apjung.backend.component.RandomStringBuilder.RandomStringBuilder;
import me.apjung.backend.domain.User.Role.Code;
import me.apjung.backend.domain.User.User;
import me.apjung.backend.domain.User.UserRole;
import me.apjung.backend.repository.Role.RoleRepotisory;
import me.apjung.backend.repository.User.UserRepository;
import me.apjung.backend.service.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Optional;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Autowired private RoleRepotisory roleRepotisory;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User
                .builder()
                .email("labyu2020@naver.com")
                .password(passwordEncoder.encode("test1234"))
                .name("testname")
                .mobile("01012345678")
                .isEmailAuth(false)
                .emailAuthToken(Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow())
                .build();

        UserRole userRole = UserRole.builder()
                .role(roleRepotisory.findRoleByCode(Code.USER).orElseThrow())
                .build();
        user.addUserRoles(userRole);
        user = userRepository.save(user);

        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(token);
        return context;
    }
}
