package me.apjung.backend.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class Thumbnail implements Serializable {
    private String publicUrl;
    private String prefix;
    private String name;
    private String extension;
    private String originalName;
    private String originalExtension;
    private Integer width;
    private Integer height;
    private Long size;
}
