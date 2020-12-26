package me.apjung.backend.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.apjung.backend.domain.base.BaseEntity;
import me.apjung.backend.domain.shop.ShopPin;
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
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRENT_TIMESTAMP WHERE `user_id`=?")
@Where(clause = "deleted_at IS NULL")
public class User extends BaseEntity implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String mobile;

    private boolean isEmailAuth;
    private String emailAuthToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ShopPin> shopPins = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, String mobile, boolean isEmailAuth, String emailAuthToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.isEmailAuth = isEmailAuth;
        this.emailAuthToken = emailAuthToken;
    }

    public User addUserRoles(UserRole userRole) {
        this.userRoles.add(userRole);
        userRole.setUser(this);
        return this;
    }
}
