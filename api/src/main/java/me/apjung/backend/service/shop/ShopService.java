package me.apjung.backend.service.shop;

import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;

public interface ShopService {
    ShopResponse.Create create(ShopRequest.Create request);
    ShopResponse.Safe safe(Long shopId, ShopSafeLevel level);
    ShopResponse.GET get(Long shopId, User user);
}
