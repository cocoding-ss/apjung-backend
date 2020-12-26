package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopPin;
import me.apjung.backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopPinRepository extends JpaRepository<ShopPin, Long> {
    Optional<ShopPin> findShopPinByShopAndUser(Shop shop, User user);
}
