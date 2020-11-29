package me.apjung.backend.repository.shop;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class ShopRepositoryImpl implements ShopRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public ShopRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
