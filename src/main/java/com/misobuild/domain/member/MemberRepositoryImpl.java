package com.misobuild.domain.member;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.misobuild.domain.member.QMember.member;

public class MemberRepositoryImpl implements MemberRepositoryQueryDsl {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Member> findBySocialIdAndSocialType(String socialId, String socialType) {

        return Optional.ofNullable(
                queryFactory
                        .select(member)
                        .from(member)
                        .where(member.socialId.eq(socialId).and(member.socialType.eq(socialType)))
                        .fetchFirst()
        );
    }
}