package me.apjung.backend.repository.shop;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.apjung.backend.domain.shop.*;
import me.apjung.backend.domain.tag.QTag;
import me.apjung.backend.domain.tag.Tag;
import me.apjung.backend.dto.projections.ShopSearchResult;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShopRepositoryImpl extends QuerydslRepositorySupport implements ShopRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ShopRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Shop.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Shop> findAllDynamicQueryOrderByName(String name, int pageNum, int pageSize) {
        return jpaQueryFactory.selectFrom(QShop.shop)
                .where(searchCondition(name))
                .orderBy(QShop.shop.name.asc())
                .limit(pageSize)
                .offset(pageNum)
                .fetch();
    }

    @Override
    public List<Shop> findAllDynamicQueryOrderByCreatedAtDesc(String name, int pageNum, int pageSize) {
//        final var fetch = jpaQueryFactory.select(Projections.fields(ShopSearchResult.class,
//                QShop.shop.id.as("shopId"),
////                QShop.shop.thumbnail.as("thumbnail"),
//                QShop.shop.name.as("name"),
//                QShop.shop.url.as("url"),
//                QShop.shop.overview.as("overview"),
//                QShop.shop.safeAt.as("safeAt"),
//                QShop.shop.safeLevel.as("shopSafeLevel")))
////                QTag.tag.name.as("tagName"),
////                QTag.tag.icon.as("tagIcon")))
        final var fetch = from(QShopTag.shopTag)

                .innerJoin(QShopTag.shopTag.tag, QTag.tag)
                                .fetchJoin()
                .rightJoin(QShopTag.shopTag.shop, QShop.shop)

                .fetchJoin()
//                .join(QShop.shop).on(QShopTag.shopTag.shop.id.eq(QShop.shop.id))
//                .join(QTag.tag).on(QShopTag.shopTag.tag.id.eq(QTag.tag.id))
            .where(deleteCondition())
            .where(searchCondition(name))
//            .select(QShop.shop)
            .groupBy(QShop.shop.id)
            .orderBy(QShop.shop.createdAt.desc())
            .limit(pageSize)
            .offset(pageNum)
            .fetch();
//        return fetch;
        return fetch.stream()
                .filter(Objects::nonNull)
                .peek(s -> System.out.println(s.getShop() + ", " + s.getTag()))
                .map(ShopTag::getShop)
                .collect(Collectors.toList());

//        fetch.stream()
//                .map()

//        return fetch;
//        return fetch.stream()
//                .map(t -> {
//                    final var temp = t.get(0, Shop.class);
//                    assert temp != null;
//                    temp.addTag(t.get(1, Tag.class));
//                    return temp;
//                })
//                .collect(Collectors.toList());
    }

    private BooleanExpression deleteCondition() {
        return QShop.shop.deletedAt.isNull()
                .and(QTag.tag.deletedAt.isNull());
    }

    private BooleanExpression searchCondition(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return QShop.shop.name.containsIgnoreCase(name)
                .or(QTag.tag.name.likeIgnoreCase(name + "%"));
    }
}
