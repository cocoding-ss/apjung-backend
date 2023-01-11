package me.apjung.backend.repository.shopviewlog;

import me.apjung.backend.JpaTest;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.domain.shop.ShopViewLog;
import me.apjung.backend.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class ShopViewLogRepositoryTest extends JpaTest {
    private final ShopViewLogRepository shopViewLogRepository;

    final User user = User.builder()
            .email("testuser@gmail.com")
            .emailAuthToken("1YPsW4mdDQz7ctPvcwGDaTMgcU7eehkK9Y3QzLWOq6QSduudE44SNhlLb6JC")
            .isEmailAuth(false)
            .mobile("01012341234")
            .name("iamtest")
            .password("$2a$10$hkWoqKEJTBqadPUuv9W/FO/UOdxEBc2ekaHf6JcLOmhCOgT7hwbiq")
            .build();

    final Shop shop = Shop.builder()
            .name("test shop2")
            .url("https://www.naver.com")
            .overview("테스트 쇼핑몰입니다3")
            .safeAt(LocalDateTime.now())
            .safeLevel(ShopSafeLevel.NORMAL)
            .build();

    @Autowired
    public ShopViewLogRepositoryTest(TestEntityManager testEntityManager, ShopViewLogRepository shopViewLogRepository) {
        super.testEntityManager = testEntityManager;
        this.shopViewLogRepository = shopViewLogRepository;
    }

    @BeforeEach
    void setUpData() {
        final var shopViewLog = ShopViewLog.builder()
                .shop(shop)
                .user(user)
                .accessedAt(LocalDate.now())
                .build();

        testEntityManager.persist(user);
        testEntityManager.persist(shop);
        testEntityManager.persist(shopViewLog);
    }

    @Test
    @DisplayName("특정 사용자가 특정 쇼핑몰을 오늘 날짜에 조회하는 테스트")
    void findShopViewLogByUserIdAndShopIdAndAccessedAtByTodayTest() {
        final var userId = user.getId();
        final var shopId = shop.getId();
        final var accessedAt = LocalDate.now();

        final var result = shopViewLogRepository.findShopViewLogByUserIdAndShopIdAndAccessedAt(userId, shopId, accessedAt);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getAccessedCount());
    }

    @Test
    @DisplayName("특정 사용자가 특정 쇼핑몰을 내일 날짜에 조회하는 테스트")
    void findShopViewLogByUserIdAndShopIdAndAccessedAtByTomorrowTest() {
        final var userId = user.getId();
        final var shopId = shop.getId();
        final var accessedAt = LocalDate.now().plus(1L, ChronoUnit.DAYS);

        final var result = shopViewLogRepository.findShopViewLogByUserIdAndShopIdAndAccessedAt(userId, shopId, accessedAt);

        assertTrue(result.isEmpty());
    }
}