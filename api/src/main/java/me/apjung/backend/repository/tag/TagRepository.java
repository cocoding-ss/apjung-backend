package me.apjung.backend.repository.tag;

import me.apjung.backend.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByName(String name);
    List<Tag> findAllByNameIgnoreCaseContaining(String name);
}
