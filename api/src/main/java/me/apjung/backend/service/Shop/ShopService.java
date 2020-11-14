package me.apjung.backend.service.Shop;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.service.Security.CustomUserDetails;

public interface ShopService {
    void create(ShopRequest.Create request);
}
