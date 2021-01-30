package me.apjung.backend.dto.request;

import lombok.*;
import me.apjung.backend.domain.shop.ShopSafeLevel;
import me.apjung.backend.service.shop.search.dto.ShopSearchOrderType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopRequest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {
        String name;
        String url;
        String overview;
        MultipartFile thumbnail;
        Set<String> tags;
        ShopSafeLevel safeLevel;
    }

    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Search {
        @Positive
        private Integer pageNum;
        @Positive
        @Max(100)
        private Integer pageSize = 10;
        private ShopSearchOrderType orderType = ShopSearchOrderType.POPULARITY;
        @NotNull
        private String keyword;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Safe {
        ShopSafeLevel safeLevel;
    }
}