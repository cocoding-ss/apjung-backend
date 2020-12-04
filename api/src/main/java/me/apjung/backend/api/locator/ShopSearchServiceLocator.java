package me.apjung.backend.api.locator;

import me.apjung.backend.service.shop.search.ShopSearchOrderByStrategy;
import me.apjung.backend.service.shop.search.ShopSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopSearchServiceLocator {
    private final List<ShopSearchService> shopSearchServices;
    private final ShopSearchService defaultShopSearchService;

    public ShopSearchServiceLocator(List<ShopSearchService> shopSearchServices,
                                    @Qualifier("shopSearchOrderByCreatedByService") ShopSearchService defaultShopSearchService) {
        this.shopSearchServices = shopSearchServices;
        this.defaultShopSearchService = defaultShopSearchService;
    }

    public ShopSearchService getSearchShopService(ShopSearchOrderByStrategy shopSearchOrderByStrategy) {
        return shopSearchServices.stream()
                .filter(s -> s.identify(shopSearchOrderByStrategy))
                .findFirst()
                .orElse(defaultShopSearchService);
    }
}