package me.apjung.backend.dto.vo;

import me.apjung.backend.domain.File.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ThumbnailTest {
    @Test
    @DisplayName("file을 thumbnail로 변환하는 테스트")
    public void fileToThumbnailTest() {
        final File file = File.builder()
                .extension("png")
                .height(720)
                .isImage(true)
                .name("test image")
                .originalExtension("jpeg")
                .originalName("test image")
                .prefix("thumbnail")
                .publicUrl("http://www.apjung.xyz")
                .size(1234L)
                .width(1280)
                .build();

        final Thumbnail expected = Thumbnail.builder()
                .extension("png")
                .height(720)
                .name("test image")
                .originalExtension("jpeg")
                .originalName("test image")
                .prefix("thumbnail")
                .publicUrl("http://www.apjung.xyz")
                .size(1234L)
                .width(1280)
                .build();

        assertEquals(expected, Thumbnail.from(file));
    }
}