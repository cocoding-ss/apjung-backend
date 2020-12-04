package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long>, CustomShopRepository {
    List<Shop> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Shop> findAllByOrderByName(Pageable pageable);

    List<Shop> findAllByNameIgnoreCaseContainingOrderByCreatedAtDesc(String name, Pageable pageable);
    List<Shop> findAllByNameIgnoreCaseContainingOrderByName(String name, Pageable pageable);
}