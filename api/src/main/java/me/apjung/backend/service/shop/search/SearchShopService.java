package me.apjung.backend.service.shop.search;

import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;

import java.util.List;

public interface SearchShopService {
    List<ShopResponse.SearchResult> search(ShopRequest.Search request);
    boolean identify(OrderBySearchShopStrategy strategy);
}
