package me.apjung.backend.repository.UserRepository;

import me.apjung.backend.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
