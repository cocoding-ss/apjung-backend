package me.apjung.backend.service.File.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SavedFile {
    private final String prefix;

    private final String originalName;
    private final String originalExtension;

    private final String name;
    private final String extension;
    private final Long size;

    private final Integer width;
    private final Integer height;

    private final boolean isImage;
    private final String publicUrl;
}
