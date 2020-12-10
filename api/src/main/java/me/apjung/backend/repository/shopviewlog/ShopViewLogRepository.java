package me.apjung.backend.repository.shopviewlog;

import me.apjung.backend.domain.shop.ShopViewLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ShopViewLogRepository extends JpaRepository<ShopViewLog, Long> {
    Optional<ShopViewLog> findShopViewLogByUserIdAndShopIdAndAccessedAt(Long userId, Long shopId, LocalDate accessedAt);
}
