package me.apjung.backend.api.locator;

import me.apjung.backend.repository.shop.ShopRepository;
import me.apjung.backend.service.shop.search.ShopSearchOrderByCreatedByService;
import me.apjung.backend.service.shop.search.ShopSearchOrderByNameService;
import me.apjung.backend.service.shop.search.ShopSearchOrderByStrategy;
import me.apjung.backend.service.shop.search.ShopSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShopSearchServiceLocatorTest {
    @Mock
    private ShopRepository shopRepository;

    private final ShopSearchService defaultShopSearchService = new ShopSearchOrderByCreatedByService(shopRepository);
    private static final ShopSearchOrderByStrategy DEFAULT_STRATEGY = ShopSearchOrderByStrategy.RECENTLY;
    
    @Test
    @DisplayName("올바른 할당 테스트")
    public void locateSuccessTest() {
        // given
        final var searchShopServiceLocator = new ShopSearchServiceLocator(List.of(
                new ShopSearchOrderByNameService(shopRepository),
                        new ShopSearchOrderByCreatedByService(shopRepository)), defaultShopSearchService);

        // when
        final var searchShopService = searchShopServiceLocator.getSearchShopService(ShopSearchOrderByStrategy.NAME);

        // then
        assertTrue(searchShopService.identify(ShopSearchOrderByStrategy.NAME));
    }

    @Test
    @DisplayName("기본 값으로 할당되는 테스트")
    public void locateSuccessByDefaultSearchShopServiceTest() {
        // given
        final var searchShopServiceLocator = new ShopSearchServiceLocator(List.of(
                new ShopSearchOrderByCreatedByService(shopRepository)), defaultShopSearchService);

        // when
        final var searchShopService = searchShopServiceLocator.getSearchShopService(ShopSearchOrderByStrategy.NAME);

        // then
        assertTrue(searchShopService.identify(DEFAULT_STRATEGY));
    }
}