package me.apjung.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

public class ShopRequest {

    @Data
    @AllArgsConstructor
    public static class Create {
        String name;
        String url;
        String overview;
        MultipartFile thumbnail;
        Set<String> tags;
    }

    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Search {
        private Integer pageNum;
        @Positive
        @Max(100) // TODO: 2020-12-03 최대 사이즈는 상황 보고 변경 가능
        private Integer pageSize = 10;
        private String orderType;
        @NotNull
        private Filter filter = Filter.NO_FILTER;

        @Setter
        @Getter
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Filter {
            private String name;

            public static final Filter NO_FILTER = new Filter(null);
        }
    }
}