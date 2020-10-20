package me.apjung.backend.repository.User;

import me.apjung.backend.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
