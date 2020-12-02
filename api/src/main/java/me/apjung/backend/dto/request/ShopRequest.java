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
        private final Filter filters;

        @Getter
        @ToString
        @AllArgsConstructor
        public static class Filter {
            String name;
//            private final FilterType filterType;
//            // TODO: 2020-11-30 나중에 범위 관련된 value가 필요하면 별도의 클래스로 분리
//            private final String filterValue;
//
//            // TODO: 2020-11-30 추후 추가되는 검색 필터의 종류에 따라 추가
//            public enum FilterType {
//                NAME
//            }
        }
    }
}
