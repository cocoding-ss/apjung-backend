package me.apjung.backend.repository.role;

import me.apjung.backend.domain.user.role.Code;
import me.apjung.backend.domain.user.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepotisory extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByCode(Code code);
}
