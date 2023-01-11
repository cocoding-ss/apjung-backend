package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.user.User;

import java.util.List;

public interface ShopPinRepositoryCustom {
    List<Shop> getPinnedShopsByUser(User user);
}
