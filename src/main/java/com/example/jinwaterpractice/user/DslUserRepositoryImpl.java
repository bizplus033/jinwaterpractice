package com.example.jinwaterpractice.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DslUserRepositoryImpl implements DslUserRepository {

    private final JPAQueryFactory query;
    private QUser user = QUser.user;

    /**
     * userId가 존재하는지
     * */
    @Override
    public boolean existsByUserId(String userId) {
        return query.selectOne()
                .from(user)
                .where(user.userId.eq(userId))
                .fetchFirst() != null;
    }
    /**
     * 삭제용 메서드
     * */
    @Override
    public void updateDeleteStateOfUser(Long[] userIds) {
        query.update(user)
                .set(user.deleteState, 1) // 1은 true이고 삭제됨을 의미
                .where(user.id.in(userIds))
                .execute();
    }
    /**
     * 특정 키워드로 모든 회원 찾기
     * findByUserAllBySearchKeyword
     * */
    @Override
    public Page<User> findByUserAllBySearchKeyword(String userId, String name, Pageable pageable) {
        BooleanExpression predicate = user.deleteState.eq(0);// 조건 : 삭제가 안된 회원 == 있는 회원
        // 회원 아이디가 있는 경우
        if (userId != null && !userId.isEmpty()) {
            predicate = predicate.and(user.userId.like("%" + userId + "%"));
        }
        // 회원 이름이 있는 경우
        if (name != null && !name.isEmpty()) {
            predicate = predicate.and(user.name.like("%" + name + "%"));
        }

        List<User> content = query.selectFrom(user)
                .where(predicate)
                .limit(pageable.getPageSize()) // 몇 개 가져 올건가
                .offset(pageable.getOffset()) // 몇 번부터 가져올건가
                .fetch();

        Long total = query.select(user.count())
                .from(user)
                .where(predicate)
                .fetchOne();

        if (total == null) total = 0L;
        return new PageImpl<>(content, pageable, total);
    }

    //    @Override
//    public Optional<User> existsByUserIdAndPassword(String userId, String password) {
//        return Optional.ofNullable(jpaQueryFactory
//                .selectFrom(QUser.user)
//                .where(QUser.user.userId.eq(userId).and(QUser.user.password.eq(password)))
//                .fetchOne());
//    }
}
