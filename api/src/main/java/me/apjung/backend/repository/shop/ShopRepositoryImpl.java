package me.apjung.backend.repository.shop;

import com.querydsl.core.FilteredClause;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import me.apjung.backend.domain.shop.*;
import me.apjung.backend.domain.tag.QTag;
import me.apjung.backend.dto.request.ShopRequest;
import me.apjung.backend.service.shop.search.dto.ShopSearchOrderType;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ShopRepositoryImpl extends QuerydslRepositorySupport implements ShopRepositoryCustom {
    public ShopRepositoryImpl() {
        super(Shop.class);
    }

    @Override
    public List<Shop> findAllBySearch(ShopRequest.Search request) {
        return this.aggregate()
                .where(searchCondition(request.getKeyword()))
                .where(safeLevelCondition())
                .where(deleteCondition())
                .groupBy(QShop.shop.id)
                .orderBy(orderByStrategy(request.getOrderType()))
                .limit(request.getPageSize())
                .offset((request.getPageNum() - 1) * request.getPageSize())
                .fetch();
    }

    private FilteredClause<JPQLQuery<Shop>> aggregate() {
        return from(QShop.shop).select(QShop.shop)
                .leftJoin(QShopTag.shopTag).on(QShop.shop.eq(QShopTag.shopTag.shop))
                .leftJoin(QTag.tag).on(QShopTag.shopTag.tag.eq(QTag.tag));
    }

    private BooleanExpression deleteCondition() {
        return QShop.shop.deletedAt.isNull()
                .and(QTag.tag.deletedAt.isNull());
    }

    private OrderSpecifier orderByStrategy(ShopSearchOrderType type) {
        if (ShopSearchOrderType.NAME.equals(type)) {
            return QShop.shop.name.asc();
        }
        if (ShopSearchOrderType.RECENTLY.equals(type)) {
            return QShop.shop.id.desc();
        }

        return QShop.shop.id.desc();
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
