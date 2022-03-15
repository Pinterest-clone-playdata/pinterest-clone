package com.team1.pinterest.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinSearchCondition;
import com.team1.pinterest.DTO.QPinDTO;
import com.team1.pinterest.Entitiy.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;

import java.util.List;

import static com.team1.pinterest.Entitiy.QFollower.*;
import static com.team1.pinterest.Entitiy.QPin.*;
import static com.team1.pinterest.Entitiy.QUser.*;
import static com.team1.pinterest.Entitiy.Role.*;
import static org.springframework.util.StringUtils.*;

public class PinRepositoryImpl implements PinRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public PinRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<PinDTO> findAllPinHome(Pageable pageable, PinSearchCondition condition) {
        List<PinDTO> fetch = queryFactory
                .select(new QPinDTO(pin))
                .from(pin)
                .leftJoin(pin.user, user)
                .fetchJoin()
                .where(pinTitleContains(condition.getTitle())
                        ,pinContentContains(condition.getContent())
                        ,pinUsernameEq(condition.getUsername())
                        ,pin.role.eq(PUBLIC))
                .orderBy(pinOrderBy(condition.getOrderBy()))
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

    @Override
    public Slice<PinDTO> findFollowersByUser(Pageable pageable, User owner) {
        List<User> find = queryFactory
                .select(follower1.followee)
                .from(follower1)
                .where(follower1.follower.eq(owner))
                .fetch();

        List<PinDTO> fetch = queryFactory
                .select(new QPinDTO(pin))
                .from(pin)
                .where(pin.user.in(find)
                        ,pin.role.eq(PUBLIC))
                .orderBy(pin.createDate.desc())
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

    @Override
    public Slice<PinDTO> findMyPins(Pageable pageable, User user, PinSearchCondition condition) {
        List<PinDTO> fetch = queryFactory
                .select(new QPinDTO(pin))
                .from(pin)
                .where(pin.user.eq(user)
                        , pinContentContains(condition.getContent())
                        , pinTitleContains(condition.getTitle()))
                .orderBy(pin.createDate.desc())
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

    // == 편의 메서드 == //

    private OrderSpecifier<?> pinOrderBy(String orderBy) {
        if (orderBy.equals("Date")){
            return pin.createDate.desc();
        }
        else{
            return pin.count.asc();
        }
    }

    private BooleanExpression pinUsernameEq(String username) {
        return hasText(username) ? pin.user.username.eq(username) : null;
    }

    private BooleanExpression pinContentContains(String content) {
        return hasText(content) ? pin.content.contains(content) : null;
    }

    private BooleanExpression pinTitleContains(String title) {
        return hasText(title) ? pin.title.contains(title) : null;
    }
}
