package me.apjung.backend.mock;

import me.apjung.backend.component.randomstringbuilder.RandomStringBuilder;
import me.apjung.backend.domain.user.role.Code;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.user.UserRole;
import me.apjung.backend.domain.user.role.Role;
import me.apjung.backend.repository.role.RoleRepository;
import me.apjung.backend.service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Optional;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
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

        user.setId(1L);
        UserRole userRole = UserRole.builder()
                .role(Role.builder().id(1L).code(Code.USER).description("사용자 권한").build())
                .build();
        user.addUserRoles(userRole);

        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(token);
        return context;
    }
}
