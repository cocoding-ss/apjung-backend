package me.apjung.backend.domain.shop;

import lombok.*;
import me.apjung.backend.domain.base.BaseEntity;
import me.apjung.backend.domain.tag.Tag;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
//@ToString
@Entity
@Table(name = "shops_tags")
@SQLDelete(sql = "UPDATE users SET deleted_at=CURRNET_TIMESTAMP WHERE `shop_tag_id`=?")
@Where(clause = "deleted_at IS NULL")
public class ShopTag extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_tag_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public ShopTag(Shop shop, Tag tag) {
        this.shop = shop;
        this.tag = tag;
    }
}