package me.apjung.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.apjung.backend.domain.shop.ShopViewLog;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.mock.MockUser;
import me.apjung.backend.component.randomstringbuilder.RandomStringBuilder;
import me.apjung.backend.domain.base.ViewStats;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.user.role.Code;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.user.UserRole;
import me.apjung.backend.repository.file.FileRepository;
import me.apjung.backend.repository.role.RoleRepotisory;
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
    @Autowired protected RoleRepotisory roleRepotisory;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private ShopRepository shopRepository;
    @Autowired private FileRepository fileRepository;
    @Autowired private ShopViewStatsRepository shopViewStatsRepository;

    protected User createNewUser(MockUser mockUser) {
        User user = User
                .builder()
                .email(mockUser.getEmail())
                .password(passwordEncoder.encode(mockUser.getPassword()))
                .name(mockUser.getName())
                .mobile(mockUser.getMobile())
                .isEmailAuth(false)
                .emailAuthToken(Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow())
                .build();

        UserRole userRole = UserRole.builder()
                .role(roleRepotisory.findRoleByCode(Code.USER).orElseThrow())
                .build();
        user.addUserRoles(userRole);
        return userRepository.save(user);
    }

    protected Shop createNewShop() {
        Shop shop = Shop.builder()
                .name("테스트용 Mock 쇼핑몰")
                .overview("테스트용 Mock 쇼핑몰입니다")
                .url("https://www.naver.com")
                .thumbnail(fileRepository.save(File.builder()
                                .name("test.jpg")
                                .extension("jpg")
                                .height(440)
                                .width(440)
                                .size(0L)
                                .isImage(true)
                                .originalExtension("jpg")
                                .originalName("test.jpg")
                                .publicUrl("http://loremflickr.com/440/440")
                                .prefix("mock/test")
                                .build()
                        )
                ).build();
        final var savedShop = shopRepository.saveAndFlush(shop);
        shopViewStatsRepository.save(ShopViewStats.builder()
                .shop(shop)
                .build());
        return savedShop;
    }

    protected String getJwtAccessToken() {
        return jwtTokenProvider.createToken(this.createNewUser(MockUser.builder().build()));
    }

    protected String getJwtAccessToken(User user) {
        return jwtTokenProvider.createToken(user);
    }
}
