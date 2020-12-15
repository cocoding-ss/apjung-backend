package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.ShopSafeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopSafeLogRepository extends JpaRepository<ShopSafeLog, Long> {
}
