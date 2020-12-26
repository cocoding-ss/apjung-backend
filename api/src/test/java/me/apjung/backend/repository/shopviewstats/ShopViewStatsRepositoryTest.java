package me.apjung.backend.repository.shopviewstats;

import me.apjung.backend.JpaTest;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

class ShopViewStatsRepositoryTest extends JpaTest {
    private ShopViewStatsRepository shopViewStatsRepository;

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
            .build();

    @Autowired
    public ShopViewStatsRepositoryTest(TestEntityManager testEntityManager, ShopViewStatsRepository shopViewStatsRepository) {
        super.testEntityManager = testEntityManager;
        this.shopViewStatsRepository = shopViewStatsRepository;
    }

    @BeforeEach
    void setUpData() {
        final var shopViewStats = ShopViewStats.builder()
                .shop(shop)
                .build();

        testEntityManager.persist(user);
        testEntityManager.persist(shop);
        testEntityManager.persist(shopViewStats);
    }

    @Test
    @DisplayName("특정 쇼핑몰의 view_stats 조회")
    @Disabled(value = "DB 데이터 의존성")
    void findShopViewStatsByShopIdTest() {
        final var shopId = shop.getId();

        final var result = shopViewStatsRepository.findShopViewStatsByShopId(shopId);

        assertTrue(result.isPresent());
    }

}