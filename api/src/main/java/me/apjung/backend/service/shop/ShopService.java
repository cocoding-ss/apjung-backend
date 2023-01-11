package me.apjung.backend.service.shop;

import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;

import java.util.List;

public interface ShopService {
    ShopResponse.Create create(ShopRequest.Create request);
    ShopResponse.Safe safe(Long shopId, ShopSafeLevel level);
    ShopResponse.GET get(Long shopId, User user);

    // 쇼핑몰 즐겨찾기 관련 서비스
    ShopResponse.CreatePin createPin(Long shopId, User currentUser) throws Exception;
    ShopResponse.DeletePin deletePin(Long shopPinId, User currentUser);
    List<ShopResponse.GET> getMyPinnedShops(User currentUser);
}
