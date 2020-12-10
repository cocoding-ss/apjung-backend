package me.apjung.backend.domain.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.apjung.backend.domain.Base.BaseEntity;
import me.apjung.backend.domain.File.File;
import me.apjung.backend.domain.shop.Shop;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tags")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRNET_TIMESTAMP WHERE `tag_id`=?")
@Where(clause = "deleted_at IS NULL")
public class Tag extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    Long id;

    String name;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private File icon;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "shops_tags",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id")
    )
    private Set<Shop> shops = new HashSet<>();

    @Builder
    public Tag(String name, File icon) {
        this.name = name;
        this.icon = icon;
    }

    public void addShop(Shop shop) {
        shops.add(shop);
    }
}
