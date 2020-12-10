package me.apjung.backend.domain.tag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.apjung.backend.domain.base.BaseEntity;
import me.apjung.backend.domain.file.File;
import me.apjung.backend.domain.shop.ShopTag;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<ShopTag> shopTags = new HashSet<>();

    @Builder
    public Tag(String name, File icon) {
        this.name = name;
        this.icon = icon;
    }
}