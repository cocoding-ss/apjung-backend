package me.apjung.backend.repository.UserRole;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserRoleRepositoryImpl implements UserRoleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public UserRoleRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
