package me.apjung.backend.repository.shop;

import me.apjung.backend.AbstractDataJpaTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

public class ShopRepositoryTest extends AbstractDataJpaTest {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopRepositoryTest(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Test
    @DisplayName("이름으로 정렬(가나다순)된 쇼핑몰 리스트 조회")
    public void findAllByOrderByNameTest() {
        shopRepository.findAllByOrderByName(Pageable.unpaged())
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("최신 등록일로 정렬된 쇼핑몰 리스트 조회")
    public void findAllByOrderByCreatedAtDescTest() {
        shopRepository.findAllByOrderByCreatedAtDesc(Pageable.unpaged())
                .forEach(System.out::println);
    }
}