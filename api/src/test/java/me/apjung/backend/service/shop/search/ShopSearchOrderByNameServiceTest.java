package me.apjung.backend.service.shop.search;

import me.apjung.backend.domain.base.ViewStats;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.shop.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShopSearchOrderByNameServiceTest {
    @InjectMocks
    private ShopSearchOrderByNameService shopSearchOrderByNameService;
    @Mock
    private ShopRepository shopRepository;

    private final File dummyFile = File.builder()
            .publicUrl("www.naver.com")
            .build();

    private final List<Shop> dummyShops = List.of(
            Shop.builder()
                    .name("test name3")
                    .overview("test overview1")
                    .url("www.apjung.xyz")
                    .thumbnail(dummyFile)
                    .build(),
            Shop.builder()
                    .name("test name5")
                    .overview("test overview2")
                    .url("www.apjung.xyz")
                    .build(),
            Shop.builder()
                    .name("test name1")
                    .overview("test overview3")
                    .url("www.apjung.xyz")
                    .build(),
            Shop.builder()
                    .name("test name1")
                    .overview("test overview4")
                    .url("www.apjung.xyz")
                    .build(),
            Shop.builder()
                    .name("test name2")
                    .overview("test overview5")
                    .url("www.apjung.xyz")
                    .build()) ;

    private final List<ShopViewStats> dummyShopViewStats = List.of(
            ShopViewStats.builder()
                    .shop(dummyShops.get(0))
                    .build(),
            ShopViewStats.builder()
                    .shop(dummyShops.get(1))
                    .build(),
            ShopViewStats.builder()
                    .shop(dummyShops.get(2))
                    .build(),
            ShopViewStats.builder()
                    .shop(dummyShops.get(3))
                    .build(),
            ShopViewStats.builder()
                    .shop(dummyShops.get(4))
                    .build());

    private final List<ViewStats> dummyViewStats = List.of(
            new ViewStats(0L, 0L),
            new ViewStats(8L, 2L),
            new ViewStats(8L, 4L),
            new ViewStats(8L, 6L),
            new ViewStats(6L, 6L));

    @Test
    @DisplayName("이름으로 쇼핑몰 정렬 테스트")
    public void orderByNameTest() {
        // given
        final var request = new ShopRequest.Search(0, dummyShops.size(), "NAME", new ShopRequest.Search.Filter(""));
        final var sortedDummyShops= dummyShops.stream()
                .sorted(Comparator.comparing(Shop::getName))
                .collect(Collectors.toList());

        final var expected = List.of("test name1", "test name1", "test name2", "test name3", "test name5");

        IntStream.range(0, dummyShops.size())
                .forEach(i -> {
                    ReflectionTestUtils.setField(dummyShopViewStats.get(i), "viewStats", dummyViewStats.get(i));
                    dummyShops.get(i).setShopViewStats(dummyShopViewStats.get(i));
                });

        given(shopRepository.findAllDynamicQueryOrderByName(anyString(), anyInt(), anyInt()))
                .willReturn(sortedDummyShops);

        // when
        final var searchResults =
                shopSearchOrderByNameService.search(request.getFilter(), request.getPageNum(), request.getPageSize());

        // then
        assertEquals(expected.size(), searchResults.size());
        assertIterableEquals(expected, searchResults.stream()
                .map(ShopResponse.SearchResult::getName)
                .collect(Collectors.toList()));
    }

    @Test
    @DisplayName("이름으로 검색 후 이름으로 쇼핑몰 정렬 테스트")
    public void containsNameOrderByNameTest() {
        // given
        final var request = new ShopRequest.Search(0, dummyShops.size(), "NAME", new ShopRequest.Search.Filter("NAME1"));
        final var sortedDummyShops= dummyShops.stream()
                .filter(s -> s.getName().toUpperCase().contains("NAME1"))
                .sorted(Comparator.comparing(Shop::getName))
                .collect(Collectors.toList());

        final var expected = List.of("test name1", "test name1");

        IntStream.range(0, dummyShops.size())
                .forEach(i -> {
                    ReflectionTestUtils.setField(dummyShopViewStats.get(i), "viewStats", dummyViewStats.get(i));
                    dummyShops.get(i).setShopViewStats(dummyShopViewStats.get(i));
                });

        given(shopRepository.findAllDynamicQueryOrderByName(anyString(), anyInt(), anyInt()))
                .willReturn(sortedDummyShops);

        // when
        final var searchResults =
                shopSearchOrderByNameService.search(request.getFilter(), request.getPageNum(), request.getPageSize());

        // then
        assertEquals(expected.size(), searchResults.size());
        assertIterableEquals(expected, searchResults.stream()
                .map(ShopResponse.SearchResult::getName)
                .collect(Collectors.toList()));
    }
}