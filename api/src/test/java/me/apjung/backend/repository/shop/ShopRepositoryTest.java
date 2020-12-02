package me.apjung.backend.repository.shop;

import me.apjung.backend.AbstractDataJpaTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

public class ShopRepositoryTest extends AbstractDataJpaTest {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopRepositoryTest(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Test
    @DisplayName("최신 등록일로 정렬된 쇼핑몰 리스트 조회")
    public void findAllByOrderByCreatedAtDescTest() {
        shopRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10))
                .forEach(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "', createdAt: " + e.getCreatedAt()));
    }

    @Test
    @DisplayName("이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회")
    public void findAllByOrderByNameTest() {
        shopRepository.findAllByOrderByName(PageRequest.of(0, 10))
                .forEach(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "'"));
    }

    @Test
    @DisplayName("이름으로 검색 후(like %name%) 최신 등록일로 정렬된 쇼핑몰 리스트 조회(hibernate)")
    @Disabled
    @Deprecated
    public void findAllByNameIgnoreCaseContainingOrderByCreatedAtDescTest() {
        shopRepository.findAllByNameIgnoreCaseContainingOrderByCreatedAtDesc("test", PageRequest.of(0, 10))
                .forEach(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "', createdAt: " + e.getCreatedAt()));
    }

    @Test
    @DisplayName("이름으로 검색 후(like %name%) 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(hibernate)")
    @Disabled
    @Deprecated
    public void findAllByNameIgnoreCaseContainingOrderByNameTest() {
        shopRepository.findAllByNameIgnoreCaseContainingOrderByName("무신", PageRequest.of(0, 10))
                .forEach(e -> System.out.println("id: " + e.getId() + ", name: '" + e.getName() + "'"));
    }

    @Test
    @DisplayName("이름으로 검색 후(like %name%) 최신 등록일로 정렬된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByCreatedAtTest() {
        final var expected = shopRepository.findAllByNameIgnoreCaseContainingOrderByCreatedAtDesc("test", PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByCreatedAtDesc("test", 0, 10);

        assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("이름으로 검색 후(like %name%) 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByNameTest() {
        final var expected = shopRepository.findAllByNameIgnoreCaseContainingOrderByName("test", PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByName("test", 0, 10);

        assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("이름이 null 행태로 들어왔을 때, 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByNameTestWhenNameIsNullTest() {
        final var expected = shopRepository.findAllByOrderByName(PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByName(null, 0, 10);

        assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("이름이 빈문자 행태로 들어왔을 때, 이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회(querydsl)")
    public void findAllDynamicQueryOrderByNameTestWhenNameIsEmptyTest() {
        final var expected = shopRepository.findAllByOrderByName(PageRequest.of(0, 10));
        final var result = shopRepository.findAllDynamicQueryOrderByName("", 0, 10);

        assertIterableEquals(expected, result);
    }
}