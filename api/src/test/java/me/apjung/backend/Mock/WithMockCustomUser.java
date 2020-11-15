package me.apjung.backend.Mock;

import me.apjung.backend.domain.User.Role.Code;
import me.apjung.backend.domain.User.UserRole;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomMockUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
}
