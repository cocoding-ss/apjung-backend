package me.apjung.backend.service.shop;

import me.apjung.backend.domain.Base.ViewStats;
import me.apjung.backend.domain.File.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.dto.response.ShopResponse;
import me.apjung.backend.repository.shop.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderByNameSearchShopServiceTest {
    @InjectMocks
    private OrderByNameSearchShopService orderByNameSearchShopService;
    @Mock
    private ShopRepository shopRepository;

    private final File dummyFile = File.builder()
            .publicUrl("www.naver.com")
            .build();

    private final List<Shop> dummyShops = List.of(
            Shop.builder()
                    .name("test name")
                    .overview("test overview1")
                    .url("www.apjung.xyz")
                    .thumbnail(dummyFile)
                    .viewStats(new ViewStats(0L, 0L))
                    .build(),
            Shop.builder()
                    .name("test name2")
                    .overview("test overview2")
                    .url("www.apjung.xyz")
                    .viewStats(new ViewStats(8L, 2L))
                    .build(),
            Shop.builder()
                    .name("test name2")
                    .overview("test overview3")
                    .url("www.apjung.xyz")
                    .viewStats(new ViewStats(8L, 4L))
                    .build(),
            Shop.builder()
                    .name("test name3")
                    .overview("test overview4")
                    .url("www.apjung.xyz")
                    .viewStats(new ViewStats(8L, 6L))
                    .build(),
            Shop.builder()
                    .name("test name")
                    .overview("test overview5")
                    .url("www.apjung.xyz")
                    .viewStats(new ViewStats(6L, 6L))
                    .build()
    );

    @Test
    @DisplayName("이름으로 쇼핑몰 정렬 테스트")
    public void test() {
        // given
        final var request = new ShopRequest.Search(1, 5, null);
        final var pageable = PageRequest.of(1, 5);
        final var expected= dummyShops.stream()
                .map(ShopResponse.SearchResult::from)
                .collect(Collectors.toList());

        given(shopRepository.findAllByOrderByName(pageable))
                .willReturn(dummyShops);

        // when
        final var searchResults = orderByNameSearchShopService.search(request);

        // then
        assertEquals(expected.size(), searchResults.size());
        IntStream.range(0, expected.size())
                .forEach(i -> {
                    assertEquals(expected.get(i).getId(), searchResults.get(i).getId());
                    assertEquals(expected.get(i).getName(), searchResults.get(i).getName());
                    assertEquals(expected.get(i).getOverview(), searchResults.get(i).getOverview());
                    assertEquals(expected.get(i).getUrl(), searchResults.get(i).getUrl());
                    assertEquals(expected.get(i).getPv(), searchResults.get(i).getPv());
                    assertEquals(expected.get(i).getUv(), searchResults.get(i).getUv());
                    assertEquals(expected.get(i).getThumbnailUrl(), searchResults.get(i).getThumbnailUrl());
                });
    }
}