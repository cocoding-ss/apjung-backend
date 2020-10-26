package me.apjung.backend.repository.Role;

import me.apjung.backend.domain.User.Role.Code;
import me.apjung.backend.domain.User.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepotisory extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByCode(Code code);
}
