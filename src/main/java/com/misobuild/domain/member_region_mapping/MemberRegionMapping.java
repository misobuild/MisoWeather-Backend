package com.misobuild.domain.member_region_mapping;

import com.misobuild.domain.member.Member;
import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "MEMBER_REGION_MAPPING_TB")
public class MemberRegionMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "STATUS")
    private RegionEnum regionEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "REGION_ID")
    private Region region;

    @Builder
    public MemberRegionMapping(RegionEnum regionEnum, Member member, Region region ) {
        this.regionEnum = regionEnum;
        this.member = member;
        this.region = region;
    }
}
