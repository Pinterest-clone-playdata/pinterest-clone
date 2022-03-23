package com.team1.pinterest.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team1.pinterest.DTO.QUserDTO;
import com.team1.pinterest.DTO.UserDTO;
import com.team1.pinterest.DTO.UserSearchCondition;
import com.team1.pinterest.Entitiy.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.team1.pinterest.Entitiy.QUser.*;
import static org.springframework.util.StringUtils.hasText;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<UserDTO> findUsersByUsername(UserSearchCondition condition) {
        return queryFactory
                .select(new QUserDTO(user))
                .from(user)
                .where(usernameEq(condition.getUsername()))
                .orderBy(user.createDate.asc())
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? user.username.contains(username) : null;
    }
}
