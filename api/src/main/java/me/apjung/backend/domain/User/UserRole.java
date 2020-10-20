package me.apjung.backend.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.apjung.backend.domain.Base.BaseEntity;
import me.apjung.backend.domain.User.Role.Code;
import me.apjung.backend.domain.User.Role.Role;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users_roles")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRNET_TIMESTAMP WHERE `user_role_id`=?")
@Where(clause = "deleted_at IS NULL")
public class UserRole extends BaseEntity implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder
    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public static UserRole create(Role role) {
        return UserRole.builder()
                .role(role)
                .build();
    }
}
