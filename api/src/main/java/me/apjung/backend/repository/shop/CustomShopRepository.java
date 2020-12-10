package me.apjung.backend.repository.shop;

import me.apjung.backend.domain.shop.Shop;

import java.util.List;

public interface CustomShopRepository {
    List<Shop> findAllDynamicQueryOrderByCreatedAtDesc(String name, int pageNum, int pageSize);
    List<Shop> findAllDynamicQueryOrderByName(String name, int pageNum, int pageSize);
}
