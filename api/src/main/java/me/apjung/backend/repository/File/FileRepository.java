package me.apjung.backend.repository.File;

import me.apjung.backend.domain.File.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
