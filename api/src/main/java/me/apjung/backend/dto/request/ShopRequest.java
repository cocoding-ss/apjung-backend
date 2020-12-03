package me.apjung.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
        @Positive
        private final Integer pageNum;
        @Positive
        @Max(100) // TODO: 2020-12-03 최대 사이즈는 상황 보고 변경 가능
        private final Integer pageSize;
        private final String orderType;
        @NotNull
        private final Filter filter;

        @Getter
        @ToString
        @AllArgsConstructor
        public static class Filter {
            private final String name;

            public static final Filter NO_FILTER = new Filter(null);
        }
    }
}
