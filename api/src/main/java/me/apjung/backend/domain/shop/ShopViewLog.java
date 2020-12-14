package me.apjung.backend.domain.shop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.apjung.backend.domain.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "shops_view_logs")
public class ShopViewLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_view_log_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "accessed_at", nullable = false)
    private LocalDate accessedAt;

    @Column(name = "accessed_count", nullable = false)
    private int accessedCount;

    @PrePersist
    private void prePersist() {
        this.accessedCount = 1;
    }

    @Builder
    public ShopViewLog(Shop shop, User user, LocalDate accessedAt) {
        this.shop = shop;
        this.user = user;
        this.accessedAt = accessedAt;
    }

    public void increaseAccessedCount() {
        this.accessedCount++;
    }
}
