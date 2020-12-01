package me.apjung.backend.api.locator;

import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.service.shop.search.OrderByCreatedBySearchShopService;
import me.apjung.backend.service.shop.search.OrderByNameSearchShopService;
import me.apjung.backend.service.shop.search.OrderBySearchShopStrategy;
import me.apjung.backend.service.shop.search.SearchShopService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchShopServiceLocatorTest {
    @Mock
    private ShopRepository shopRepository;

    private final SearchShopService defaultSearchShopService = new OrderByCreatedBySearchShopService(shopRepository);
    private static final OrderBySearchShopStrategy DEFAULT_STRATEGY = OrderBySearchShopStrategy.RECENTLY;
    
    @Test
    @DisplayName("올바른 할당 테스트")
    public void locateSuccessTest() {
        // given
        final var searchShopServiceLocator = new SearchShopServiceLocator(List.of(
                new OrderByNameSearchShopService(shopRepository),
                        new OrderByCreatedBySearchShopService(shopRepository)), defaultSearchShopService);

        // when
        final var searchShopService = searchShopServiceLocator.getSearchShopService(OrderBySearchShopStrategy.NAME);

        // then
        assertTrue(searchShopService.identify(OrderBySearchShopStrategy.NAME));
    }

    @Test
    @DisplayName("기본 값으로 할당되는 테스트")
    public void locateSuccessByDefaultSearchShopServiceTest() {
        // given
        final var searchShopServiceLocator = new SearchShopServiceLocator(List.of(
                new OrderByCreatedBySearchShopService(shopRepository)), defaultSearchShopService);

        // when
        final var searchShopService = searchShopServiceLocator.getSearchShopService(OrderBySearchShopStrategy.NAME);

        // then
        assertTrue(searchShopService.identify(DEFAULT_STRATEGY));
    }
}