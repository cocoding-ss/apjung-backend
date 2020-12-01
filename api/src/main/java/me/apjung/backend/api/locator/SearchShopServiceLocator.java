package me.apjung.backend.api.locator;

import me.apjung.backend.service.shop.search.OrderBySearchShopStrategy;
import me.apjung.backend.service.shop.search.SearchShopService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchShopServiceLocator {
    private final List<SearchShopService> searchShopServices;
    private final SearchShopService defaultSearchShopService;

    public SearchShopServiceLocator(List<SearchShopService> searchShopServices,
                                    @Qualifier("orderByCreatedBySearchShopService") SearchShopService defaultSearchShopService) {
        this.searchShopServices = searchShopServices;
        this.defaultSearchShopService = defaultSearchShopService;
    }

    public SearchShopService getSearchShopService(OrderBySearchShopStrategy orderBySearchShopStrategy) {
        return searchShopServices.stream()
                .filter(s -> s.identify(orderBySearchShopStrategy))
                .findFirst()
                .orElse(defaultSearchShopService);
    }
}