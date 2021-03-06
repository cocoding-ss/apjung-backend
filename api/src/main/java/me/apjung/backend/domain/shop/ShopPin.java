package me.apjung.backend.domain.shop;

import lombok.*;
import me.apjung.backend.domain.base.BaseEntity;
import me.apjung.backend.domain.user.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "shops_pin")
@SQLDelete(sql = "UPDATE shops_pin SET deleted_at=CURRENT_TIMESTAMP WHERE `shop_pin_id`=?")
@Where(clause = "deleted_at IS NULL")
public class ShopPin extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_pin_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
