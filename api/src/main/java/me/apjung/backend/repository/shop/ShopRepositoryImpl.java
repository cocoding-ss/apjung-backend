package me.apjung.backend.repository.shop;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.apjung.backend.domain.shop.QShop;
import me.apjung.backend.domain.shop.QShopTag;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.tag.QTag;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.set;

public class ShopRepositoryImpl implements ShopRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ShopRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
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
        return jpaQueryFactory.select(QShop.shop)
                .from(QShopTag.shopTag)
                    .rightJoin(QShopTag.shopTag.shop, QShop.shop)
//                        .on(QShop.shop.id.eq(QShopTag.shopTag.shop.id))
//                        .fetchJoin()
                    .leftJoin(QShopTag.shopTag.tag, QTag.tag)
//                        .on(QShopTag.shopTag.tag.id.eq(QTag.tag.id))
//                        .fetchJoin()
                .where(searchCondition(name))
                .groupBy(QShop.shop.id)
//                .distinct()
                .orderBy(QShop.shop.createdAt.desc())
                .limit(pageSize)
                .offset(pageNum)
                .fetch();
    }

    private BooleanExpression searchCondition(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return QShop.shop.name.containsIgnoreCase(name)
                .or(QTag.tag.name.likeIgnoreCase(name + "%"));
    }
}
