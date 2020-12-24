package me.apjung.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.mock.MockUser;
import me.apjung.backend.component.randomstringbuilder.RandomStringBuilder;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.user.role.Code;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.user.UserRole;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.role.RoleRepository;
import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.repository.shop_view_stats.ShopViewStatsRepository;
import me.apjung.backend.repository.user.UserRepository;
import me.apjung.backend.service.security.JwtTokenProvider;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Disabled
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.apjung.xyz")
public abstract class MvcTest {
    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired protected UserRepository userRepository;
    @Autowired protected RoleRepository roleRepository;
}
