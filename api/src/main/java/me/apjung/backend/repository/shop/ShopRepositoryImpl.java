package me.apjung.backend.repository.shop;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.apjung.backend.domain.shop.*;
import me.apjung.backend.domain.tag.QTag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.util.List;

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
        return from(QShopTag.shopTag)
                .select(QShop.shop)
                    .innerJoin(QShopTag.shopTag.tag, QTag.tag)
                    .rightJoin(QShopTag.shopTag.shop, QShop.shop)
                .where(deleteCondition())
                .where(searchCondition(name))
                .groupBy(QShop.shop.id)
                .orderBy(QShop.shop.createdAt.desc())
                .limit(pageSize)
                .offset(pageNum)
                .fetch();
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
