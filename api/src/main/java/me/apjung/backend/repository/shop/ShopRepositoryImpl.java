package me.apjung.backend.repository.shop;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.apjung.backend.domain.shop.QShop;
import me.apjung.backend.domain.shop.Shop;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class ShopRepositoryImpl implements CustomShopRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ShopRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Shop> findAllDynamicQueryOrderByName(String name, int pageNum, int pageSize) {
        return jpaQueryFactory.selectFrom(QShop.shop)
                .where(containsIgnoreCase(name))
                .orderBy(QShop.shop.name.asc())
                .limit(pageSize)
                .offset(pageNum)
                .fetch();
    }

    @Override
    public List<Shop> findAllDynamicQueryOrderByCreatedAtDesc(String name, int pageNum, int pageSize) {
        return jpaQueryFactory.selectFrom(QShop.shop)
                .where(containsIgnoreCase(name))
                .orderBy(QShop.shop.createdAt.desc())
                .limit(pageSize)
                .offset(pageNum)
                .fetch();
    }

    private BooleanExpression containsIgnoreCase(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        return QShop.shop.name.containsIgnoreCase(name);
    }
}
