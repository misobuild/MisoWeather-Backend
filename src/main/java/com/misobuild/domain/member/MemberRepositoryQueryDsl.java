package com.misobuild.domain.member;

import java.util.Optional;

public interface MemberRepositoryQueryDsl {
    Optional<Member> findBySocialIdAndSocialType(String socialId, String socialType);
}
