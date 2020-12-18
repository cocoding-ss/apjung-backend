package me.apjung.backend.dto.response;

import lombok.*;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.shop.ShopViewStats;
import me.apjung.backend.dto.vo.Thumbnail;

import java.io.Serializable;
import java.util.Optional;

public class ShopResponse implements Serializable {
    @Data
    @AllArgsConstructor
    @Builder
    public static class GET {
        private Long id;
        private String name;
        private String overview;
        private String url;
        private Thumbnail thumbnail;

        public static GET from(Shop shop) {
            return builder()
                    .id(shop.getId())
                    .name(shop.getName())
                    .url(shop.getUrl())
                    .overview(shop.getOverview())
                    .thumbnail(Thumbnail.from(shop.getThumbnail()))
                    .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @Builder
    public static class SearchResult {
        private final Long id;
        private final String name;
        private final String overview;
        private final String url;
        private final Long pv;
        private final Long uv;
        private final String thumbnailUrl;

        public static SearchResult from(Shop shop) {
            ShopViewStats shopViewStats = shop.getShopViewStats();
            return builder()
                    .id(shop.getId())
                    .name(shop.getName())
                    .url(shop.getUrl())
                    .overview(shop.getOverview())
                    .pv(shopViewStats.getViewStats().getPageView())
                    .uv(shopViewStats.getViewStats().getUniqueVisitor())
                    .thumbnailUrl(Optional.ofNullable(Thumbnail.from(shop.getThumbnail()))
                            .map(Thumbnail::getPublicUrl)
                            .orElse(null))
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Create {
        private Long id;
    }
}
