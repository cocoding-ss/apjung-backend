package me.apjung.backend.repository.shopviewstats;

import me.apjung.backend.domain.shop.ShopViewStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopViewStatsRepository extends JpaRepository<ShopViewStats, Long> {
    Optional<ShopViewStats> findShopViewStatsByShopId(Long shopId);
}
