package me.apjung.backend.service.shop;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;

public interface ShopService {
    ShopResponse.Create create(ShopRequest.Create request);
    ShopResponse.GET get(Long shopId);
}