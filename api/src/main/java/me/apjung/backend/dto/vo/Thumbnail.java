package me.apjung.backend.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import me.apjung.backend.domain.File.File;

import java.io.Serializable;
import java.util.Optional;

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

    public static Thumbnail from(File file) {
        return Optional.ofNullable(file)
                .map(f -> builder()
                        .name(f.getName())
                        .extension(f.getExtension())
                        .originalName(f.getOriginalName())
                        .originalExtension(f.getOriginalExtension())
                        .prefix(f.getPrefix())
                        .publicUrl(f.getPublicUrl())
                        .size(f.getSize())
                        .width(f.getWidth())
                        .height(f.getHeight())
                        .build())
                .orElse(null);
    }
}
