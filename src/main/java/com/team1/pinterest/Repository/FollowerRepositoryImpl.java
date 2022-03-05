package com.team1.pinterest.Repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team1.pinterest.DTO.FollowerDTO;
import com.team1.pinterest.DTO.FollowerSearchCondition;
import com.team1.pinterest.DTO.QFollowerDTO;
import com.team1.pinterest.Entitiy.QFollower;
import com.team1.pinterest.Entitiy.QUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.team1.pinterest.Entitiy.QFollower.*;
import static com.team1.pinterest.Entitiy.QUser.*;
import static org.springframework.util.StringUtils.*;

public class FollowerRepositoryImpl implements FollowerRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public FollowerRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<FollowerDTO> findFollowerWithSlice(Pageable pageable, FollowerSearchCondition condition) {
        List<FollowerDTO> fetch = queryFactory
                .select(new QFollowerDTO(follower1))
                .from(follower1)
                .leftJoin(follower1.followee, user)
                .fetchJoin()
                .leftJoin(follower1.follower, user)
                .fetchJoin()
                .where(followerIdEQ(condition.getFollowerId())
                        ,followeeIdEQ(condition.getFolloweeId())
                        ,followeeNameEQ(condition.getFolloweeName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (fetch.size() > pageable.getPageSize()){
            fetch.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(fetch, pageable, hasNext);
    }

    private BooleanExpression followeeNameEQ(String followeeName) {
        return hasText(followeeName) ? follower1.followee.username.eq(followeeName) : null;
    }

    private BooleanExpression followeeIdEQ(Long followeeId) {
        return followeeId != null ? follower1.followee.id.eq(followeeId) : null;
    }

    private BooleanExpression followerIdEQ(Long followerId) {
        return followerId != null ? follower1.follower.id.eq(followerId) : null;
    }
}
