package me.apjung.backend.domain.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.apjung.backend.domain.base.BaseEntity;
import me.apjung.backend.service.file.dto.SavedFile;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "files")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRNET_TIMESTAMP WHERE `file_id`=?")
@Where(clause = "deleted_at IS NULL")
public class File extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    private String prefix;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "original_extension")
    private String originalExtension;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long size;

    private Integer width;
    private Integer height;

    @Column(name = "is_image")
    private boolean isImage;

    @Column(name = "public_url")
    private String publicUrl;

    @Builder
    public File(Long id, String prefix, String originalName, String originalExtension, String name, String extension, Long size, Integer width, Integer height, boolean isImage, String publicUrl) {
        this.id = id;
        this.prefix = prefix;
        this.originalName = originalName;
        this.originalExtension = originalExtension;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.width = width;
        this.height = height;
        this.isImage = isImage;
        this.publicUrl = publicUrl;
    }

    public static File create(SavedFile file) {
        return File.builder()
                .prefix(file.getPrefix())
                .name(file.getName())
                .extension(file.getExtension())
                .originalName(file.getOriginalName())
                .originalExtension(file.getOriginalExtension())
                .publicUrl(file.getPublicUrl())
                .height(file.getHeight())
                .width(file.getWidth())
                .size(file.getSize())
                .build();
    }
}
