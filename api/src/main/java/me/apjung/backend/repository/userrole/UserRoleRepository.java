package me.apjung.backend.repository.userrole;

import me.apjung.backend.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, UserRoleRepositoryCustom {
}
