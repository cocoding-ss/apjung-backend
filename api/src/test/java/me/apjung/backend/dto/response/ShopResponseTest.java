package me.apjung.backend.dto.response;

import me.apjung.backend.domain.base.ViewStats;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.dto.vo.Thumbnail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopResponseTest {
    private final File file = File.builder()
            .publicUrl("storage.apjung.xyz")
            .build();
    private final Shop shop = Shop.builder()
            .name("test name")
            .url("test url")
            .overview("test overview")
            .thumbnail(file)
            .build();

    @Test
    @DisplayName("shop을 GET으로 변환하는 테스트")
    public void shopToShopResponseGETTest() {
        final ShopResponse.GET expected = ShopResponse.GET.builder()
                .name("test name")
                .url("test url")
                .overview("test overview")
                .thumbnail(Thumbnail.from(file))
                .build();

        assertEquals(expected, ShopResponse.GET.from(shop));
    }

    @Test
    @DisplayName("shop을 SearchResult로 변환하는 테스트")
    public void shopToShopResponseSearchResultTest() {
        final ShopResponse.SearchResult expected = ShopResponse.SearchResult.builder()
                .name("test name")
                .url("test url")
                .overview("test overview")
                .pv(0L)
                .uv(0L)
                .thumbnailUrl(Thumbnail.from(file)
                        .getPublicUrl())
                .build();

        shop.setShopViewStats(ShopViewStats.builder().build());
        final ShopResponse.SearchResult result = ShopResponse.SearchResult.from(shop);

        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getOverview(), result.getOverview());
        assertEquals(expected.getUrl(), result.getUrl());
        assertEquals(expected.getPv(), result.getPv());
        assertEquals(expected.getUv(), result.getUv());
        assertEquals(expected.getThumbnailUrl(), result.getThumbnailUrl());
    }
}