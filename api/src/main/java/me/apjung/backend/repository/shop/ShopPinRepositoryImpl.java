package me.apjung.backend.repository.shop;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.apjung.backend.domain.shop.QShop;
import me.apjung.backend.domain.shop.QShopPin;
import me.apjung.backend.domain.shop.Shop;
import me.apjung.backend.domain.user.User;

import java.util.List;

public class ShopPinRepositoryImpl implements ShopPinRepositoryCustom {
    private final JPAQueryFactory query;

    public ShopPinRepositoryImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public List<Shop> getPinnedShopsByUser(User user) {
        return query.select(QShop.shop)
                .from(QShop.shop)
                .join(QShopPin.shopPin).on(QShopPin.shopPin.shop.eq(QShop.shop).and(QShopPin.shopPin.deletedAt.isNull()))
                .where(QShopPin.shopPin.user.eq(user))
                .fetch();
    }
}
