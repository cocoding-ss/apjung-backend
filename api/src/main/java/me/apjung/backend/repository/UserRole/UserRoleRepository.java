package me.apjung.backend.repository.UserRole;

import me.apjung.backend.domain.User.Role.Code;
import me.apjung.backend.domain.User.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, UserRoleRepositoryCustom {
}
