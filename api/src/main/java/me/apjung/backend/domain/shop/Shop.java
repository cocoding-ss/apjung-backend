package me.apjung.backend.domain.shop;

import lombok.*;
import me.apjung.backend.domain.Base.BaseEntity;
import me.apjung.backend.domain.Base.ViewStats;
import me.apjung.backend.domain.File.File;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "shops")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRNET_TIMESTAMP WHERE `shop_id`=?")
@Where(clause = "deleted_at IS NULL")
public class Shop extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File thumbnail;

    private String name;
    private String url;
    private String overview;

    @Embedded
    private ViewStats viewStats;

//    @Transient
//    private Double popularity = viewStats.getPageView() * 0.4 + viewStats.getUniqueView() * 0.6;

    @Builder
    public Shop(File thumbnail, String name, String url, String overview, ViewStats viewStats) {
        this.thumbnail = thumbnail;
        this.name = name;
        this.url = url;
        this.overview = overview;
        this.viewStats = viewStats;
    }
}
