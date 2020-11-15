package me.apjung.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import me.apjung.backend.dto.vo.Thumbnail;

import java.io.Serializable;

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
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class Create {
        private Long id;
    }
}
