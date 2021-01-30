package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.dto.request.ShopRequest;

import java.util.List;

public interface ShopRepositoryCustom {
    List<Shop> findAllBySearch(ShopRequest.Search request);
}
