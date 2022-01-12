package com.misobuild.domain.member;

import java.util.Optional;

public interface MemberRepositoryQuerydsl {
    Optional<Member> findBySocialIdAndSocialType(String socialId, String socialType);
}
