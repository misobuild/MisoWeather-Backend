package com.misobuild.domain.member_region_mapping;

import com.misobuild.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRegionMappingRepository extends JpaRepository<MemberRegionMapping, Long> {
    List<MemberRegionMapping> findMemberRegionMappingByMember(Member member);
}