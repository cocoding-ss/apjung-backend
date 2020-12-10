package me.apjung.backend.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @Getter
    @ToString
    @AllArgsConstructor
    public static class Search {
        private final Integer pageNum;
        private final Integer pageSize;
        // TODO: 2020-11-29 검색 조건에 대한 클래스 필요 -> 조건의 종류, 조건 값 등
        private final List<String> searchFilters;
    }
}
