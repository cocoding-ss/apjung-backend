package me.apjung.backend.repository.tag;

import me.apjung.backend.JpaTest;
import me.apjung.backend.domain.tag.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TagRepositoryTest extends JpaTest {
    private final TagRepository tagRepository;

    @Autowired
    public TagRepositoryTest(TestEntityManager testEntityManager, TagRepository tagRepository) {
        super.testEntityManager = testEntityManager;
        this.tagRepository = tagRepository;
    }

    @BeforeEach
    void setUpData() {
        final var tags = List.of(
                Tag.builder().name("테").build(),
                Tag.builder().name("스").build(),
                Tag.builder().name("트").build(),
                Tag.builder().name("테스트").build(),
                Tag.builder().name("test").build(),
                Tag.builder().name("TesT").build());
        tags.forEach(t -> testEntityManager.persist(t));
    }

    @Test
    @DisplayName("이름으로 태그 검색(일치)")
    void findTagByNameTest() {
        assertDoesNotThrow(() -> {
            final var result = tagRepository.findTagByName("테스트");
            assertEquals("테스트", result.orElseThrow().getName());
        });
        assertThrows(RuntimeException.class, () -> {
            tagRepository.findTagByName("x테스트x").orElseThrow();
        });
    }

    @Test
    @DisplayName("이름으로 태그 검색(like %name% 연산 - containing 테스트)")
    void findAllByNameContainingTest() {
        final var result = tagRepository.findAllByNameIgnoreCaseContaining("스");
        assertIterableEquals(List.of("스", "테스트"), result.stream().map(Tag::getName).collect(Collectors.toList()));
    }

    @Test
    @DisplayName("이름으로 태그 검색(like %name% 연산 - ignore_case 테스트)")
    void findAllByNameIgnoreCaseTest() {
        final var result = tagRepository.findAllByNameIgnoreCaseContaining("test");
        assertIterableEquals(List.of("test", "TesT"), result.stream().map(Tag::getName).collect(Collectors.toList()));
    }
}