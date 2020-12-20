package me.apjung.backend.repository.shop;

import me.apjung.backend.AbstractDataJpaTest;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.domain.shop.Shop;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ShopRepositoryTest extends AbstractDataJpaTest {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopRepositoryTest(TestEntityManager testEntityManager, ShopRepository shopRepository) {
        super.testEntityManager = testEntityManager;
        this.shopRepository = shopRepository;
    }

    @BeforeEach
    public void setUpData() {
        final var user = User.builder()
                .email("testuser@gmail.com")
                .emailAuthToken("1YPsW4mdDQz7ctPvcwGDaTMgcU7eehkK9Y3QzLWOq6QSduudE44SNhlLb6JC")
                .isEmailAuth(false)
                .mobile("01012341234")
                .name("iamtest")
                .password("$2a$10$hkWoqKEJTBqadPUuv9W/FO/UOdxEBc2ekaHf6JcLOmhCOgT7hwbiq")
                .build();

        final var file = File.builder()
                .prefix("LOCAL/public/")
                .name("pBhvKVzqU8vhYi76Kb8Xgm78xU1w54VaVI8JkWv4weXiuh0pkyFAnSm9g7gB.jpg")
                .extension("jpg")
                .originalName("440x440.jpg")
                .originalExtension("jpg")
                .publicUrl("https://storage.apjung.xyz/LOCAL/public/pBhvKVzqU8vhYi76Kb8Xgm78xU1w54VaVI8JkWv4weXiuh0pkyFAnSm9g7gB.jpg")
                .size(20705L)
                .width(440)
                .height(440)
                .isImage(false)
                .build();

        final var shops = List.of(
                Shop.builder()
                        .thumbnail(file)
                        .name("test shop")
                        .url("https://www.naver.com")
                        .overview("테스트 쇼핑몰입니다1")
                        .build(),
                Shop.builder()
                        .name("무신sa TOAST")
                        .url("https://www.naver.com")
                        .overview("테스트 쇼핑몰입니다2")
                        .build(),
                Shop.builder()
                        .name("test shop2")
                        .url("https://www.naver.com")
                        .overview("테스트 쇼핑몰입니다3")
                        .build(),
                Shop.builder()
                        .name("4XR test")
                        .url("https://www.naver.com")
                        .overview("테스트 쇼핑몰입니다4")
                        .build(),
                Shop.builder()
                        .name("12 무신사 test shop")
                        .url("https://www.naver.com")
                        .overview("테스트 쇼핑몰입니다5")
                        .build());

        testEntityManager.persist(user);
        testEntityManager.persist(file);
        shops.forEach(s -> testEntityManager.persist(s));
    }

    @Test
    @Disabled(value = "DB 데이터 의존성")
    @DisplayName("최신 등록일로 정렬된 쇼핑몰 리스트 조회")
    public void findAllByOrderByCreatedAtDescTest() {
        final var expected = List.of(
                "테스트 쇼핑몰입니다5", "테스트 쇼핑몰입니다4", "테스트 쇼핑몰입니다3", "테스트 쇼핑몰입니다2", "테스트 쇼핑몰입니다1");

        final var result = shopRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10))
                .stream()
                .peek(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "' overview: '" + e.getOverview() +"', createdAt: " + e.getCreatedAt()))
                .map(Shop::getOverview)
                .collect(Collectors.toList());

        assertIterableEquals(expected, result);
    }

    @Test
    @Disabled(value = "DB 데이터 의존성")
    @DisplayName("이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회")
    public void findAllByOrderByNameTest() {
        final var expected = List.of(
                "테스트 쇼핑몰입니다5", "테스트 쇼핑몰입니다4", "테스트 쇼핑몰입니다1", "테스트 쇼핑몰입니다3", "테스트 쇼핑몰입니다2");

        final var result = shopRepository.findAllByOrderByName(PageRequest.of(0, 10))
                .stream()
                .peek(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "'"))
                .map(Shop::getOverview)
                .collect(Collectors.toList());

        assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("이름으로 검색 후(like %name%) 최신 등록일로 정렬된 쇼핑몰 리스트 조회(hibernate)")
    @Disabled(value = "DB 데이터 의존성")
    @Deprecated
    public void findAllByNameIgnoreCaseContainingOrderByCreatedAtDescTest() {
        final var expected = List.of(
                "테스트 쇼핑몰입니다5", "테스트 쇼핑몰입니다4", "테스트 쇼핑몰입니다3", "테스트 쇼핑몰입니다1");

        final var result = shopRepository.findAllByNameIgnoreCaseContainingOrderByCreatedAtDesc("test", PageRequest.of(0, 10))
                .stream()
                .peek(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "', createdAt: " + e.getCreatedAt()))
                .map(Shop::getOverview)
                .collect(Collectors.toList());

        assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("이름으로 검색 후(like %name%) 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(hibernate)")
    @Disabled(value = "DB 데이터 의존성")
    @Deprecated
    public void findAllByNameIgnoreCaseContainingOrderByNameTest() {
        final var expected = List.of(
                "테스트 쇼핑몰입니다5", "테스트 쇼핑몰입니다2");

        final var result = shopRepository.findAllByNameIgnoreCaseContainingOrderByName("무신", PageRequest.of(0, 10))
                .stream()
                .peek(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "'"))
                .map(Shop::getOverview)
                .collect(Collectors.toList());

        assertIterableEquals(expected, result);
    }

    @Test
    @Disabled(value = "DB 데이터 의존성")
    @DisplayName("이름으로 검색 후(like %name%) 최신 등록일로 정렬된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByCreatedAtTest() {
        final var expected = shopRepository.findAllByNameIgnoreCaseContainingOrderByCreatedAtDesc("test", PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByCreatedAtDesc("test", 0, 10);
        assertEquals(expected.size(), result.size());
        assertIterableEquals(expected, result);
    }

    @Test
    @Disabled(value = "DB 데이터 의존성")
    @DisplayName("이름으로 검색 후(like %name%) 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByNameTest() {
        final var expected = shopRepository.findAllByNameIgnoreCaseContainingOrderByName("test", PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByName("test", 0, 10);

        assertEquals(expected.size(), result.size());
        assertIterableEquals(expected, result);
    }

    @Test
    @Disabled
    @DisplayName("이름이 null 행태로 들어왔을 때, 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByNameTestWhenNameIsNullTest() {
        final var expected = shopRepository.findAllByOrderByName(PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByName(null, 0, 10);

        assertEquals(expected.size(), result.size());
        assertIterableEquals(expected, result);
    }

    @Test
    @Disabled
    @DisplayName("이름이 빈문자 행태로 들어왔을 때, 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByNameTestWhenNameIsEmptyTest() {
        final var expected = shopRepository.findAllByOrderByName(PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByName("", 0, 10);

        assertEquals(expected.size(), result.size());
        assertIterableEquals(expected, result);
    }
}