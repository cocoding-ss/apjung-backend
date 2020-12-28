package me.apjung.backend.repository.shop;

import com.querydsl.core.types.dsl.BooleanExpression;
import me.apjung.backend.domain.shop.*;
import me.apjung.backend.domain.tag.QTag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ShopRepositoryImpl extends QuerydslRepositorySupport implements ShopRepositoryCustom {
    public ShopRepositoryImpl() {
        super(Shop.class);
    }

    @Override
    public List<Shop> findAllDynamicQueryOrderByName(String name, int pageNum, int pageSize) {
        return from(QShop.shop)
                .select(QShop.shop)
                    .leftJoin(QShopTag.shopTag).on(QShop.shop.eq(QShopTag.shopTag.shop))
                    .leftJoin(QTag.tag).on(QShopTag.shopTag.tag.eq(QTag.tag))
                .where(deleteCondition())
                .where(searchCondition(name))
                .where(safeLevelCondition())
                .groupBy(QShop.shop.id)
                .orderBy(QShop.shop.name.asc())
                .limit(pageSize)
                .offset(pageNum)
                .fetch();
    }

    @Override
    public List<Shop> findAllDynamicQueryOrderByCreatedAtDesc(String name, int pageNum, int pageSize) {
        return from(QShop.shop)
                .select(QShop.shop)
                    .leftJoin(QShopTag.shopTag).on(QShop.shop.eq(QShopTag.shopTag.shop))
                    .leftJoin(QTag.tag).on(QShopTag.shopTag.tag.eq(QTag.tag))
                .where(deleteCondition())
                .where(searchCondition(name))
                .where(safeLevelCondition())
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

    private BooleanExpression safeLevelCondition() {
        return QShop.shop.safeLevel.in(ShopSafeLevel.NORMAL, ShopSafeLevel.SAFE);
    }
}
