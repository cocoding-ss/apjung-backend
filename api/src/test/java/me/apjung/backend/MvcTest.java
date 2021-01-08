package me.apjung.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.apjung.backend.component.custommessagesourceresolver.CustomMessageSourceResolver;
import me.apjung.backend.config.SecurityConfig;
import me.apjung.backend.config.WebMvcConfig;
import me.apjung.backend.property.JwtProps;
import me.apjung.backend.property.SecurityProps;
import me.apjung.backend.service.security.CustomUserDetailsService;
import me.apjung.backend.service.security.AccessTokenProvider;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Disabled
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.apjung.xyz")
@Import({
        WebMvcConfig.class,
        CustomMessageSourceResolver.class,

        SecurityConfig.class,
        AccessTokenProvider.class,

        SecurityProps.class,
        JwtProps.class
})
public abstract class MvcTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean protected CustomUserDetailsService customUserDetailsService;
}
