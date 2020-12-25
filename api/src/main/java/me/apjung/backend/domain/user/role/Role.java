package me.apjung.backend.domain.user.role;

import lombok.*;
import me.apjung.backend.domain.base.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "roles")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRNET_TIMESTAMP WHERE `role_id`=?")
@Where(clause = "deleted_at IS NULL")
public class Role extends BaseEntity implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Code code;
    private String description;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.code.name();
    }
}
