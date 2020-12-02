package me.apjung.backend.repository.User;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NoArgsConstructor;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
