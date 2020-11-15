package me.apjung.backend.service.File.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
public class SavedFile {
    private String prefix;

    private String originalName;
    private String originalExtension;

    private String name;
    private String extension;
    private Long size;

    private Integer width;
    private Integer height;

    private boolean isImage;
    private String publicUrl;
}
