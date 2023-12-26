package com.example.jinwaterpractice.userlog;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jinwaterpractice.user.QUser.user;
import static com.example.jinwaterpractice.userlog.QUserLog.userLog;

@Repository
@RequiredArgsConstructor
public class DslUserLogRepository {

    private final JPAQueryFactory query;

    public Page<UserLog> findUserLogAllByKeyword(String userId, String username, Pageable pageable) {
        List<UserLog> content = query.selectFrom(userLog)
                .leftJoin(userLog.user, user) // target, alias
                .where(likeUserId(userId), likeUserName(username)) // and로 연결
                .orderBy(userLog.createdAt.desc())
                .limit(pageable.getPageSize()) // 한 페이지에 표시할 아이템 개수
                .offset(pageable.getOffset())
                .fetch();

        Long total = query
                .select(userLog.count())
                .from(userLog)
                .leftJoin(userLog.user, user) // target, alias
                .where(likeUserId(userId), likeUserName(username))
                .fetchOne();

        if (total == null) total = 0L;

        return new PageImpl<>(content, pageable, total);
    }

    public BooleanExpression likeUserId(String userId) {
        if (userId == null) return null;
        return user.userId.like("%" + userId + "%");
    }

    public BooleanExpression likeUserName(String username) {
        if (username == null) return null;
        return user.userId.like("%" + username + "%");
    }
}
