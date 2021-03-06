package me.apjung.backend.domain.shop;

import lombok.*;
import me.apjung.backend.domain.base.BaseEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "shops_safe_logs")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRENT_TIMESTAMP WHERE `shop_safe_log_id`=?")
@Where(clause = "deleted_at IS NULL")
public class ShopSafeLog extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_safe_log_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "safe_at")
    private LocalDateTime safeAt;

    @Column(name = "safe_level")
    @Enumerated(value = EnumType.STRING)
    private ShopSafeLevel safeLevel;
}
