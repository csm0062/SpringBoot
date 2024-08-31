package com.bit.springboard.repository.impl;

import com.bit.springboard.entity.Member;
import com.bit.springboard.repository.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

// Q도메인에 있는걸 static으로 가지고 와야한다.
import static com.bit.springboard.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member searchOne(Long id) {
        return jpaQueryFactory.selectFrom(member)
                .orderBy(member.id.desc())
                .where(member.id.eq(id).and(member.nickname.contains("bit")))
                .fetchOne();
    }

    @Override
    public String searchUsername(Long id) {
        return jpaQueryFactory.select(member.username)
                .from(member)
                .where(member.id.eq(id))
                .fetchOne();
    }
}
