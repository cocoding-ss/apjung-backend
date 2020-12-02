package me.apjung.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopRequest {
    @Data
    @AllArgsConstructor
    public static class Create {
        String name;
        String url;
        String overview;
        MultipartFile thumbnail;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Search {
        private final Integer pageNum;
        private final Integer pageSize;
        private final String orderType;
        private final Filter filter;

        @Getter
        @ToString
        @AllArgsConstructor
        public static class Filter {
            String name;

            public static final Filter NO_FILTER = new Filter(null);
        }
    }
}
