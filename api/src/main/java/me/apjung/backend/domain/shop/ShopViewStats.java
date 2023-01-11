package me.apjung.backend.domain.shop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.apjung.backend.domain.base.ViewStats;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "shops_view_stats")
public class ShopViewStats {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_view_stats_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Embedded
    private ViewStats viewStats;

    @Builder
    public ShopViewStats(Shop shop) {
        this.shop = shop;
        viewStats = new ViewStats();
    }

    public void visit() {
        this.viewStats.visit();
    }

    public void firstVisit() {
        this.viewStats.firstVisit();
    }
}
