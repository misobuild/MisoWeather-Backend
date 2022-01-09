package com.misobuild.domain.member;

import com.misobuild.domain.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Member extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "SOCIAL_ID", nullable = false)
    private String socialId;

    @Column(name = "SOCIAL_TYPE", nullable = false)
    private String socialType;

    @Column(name = "NICKNAME")
    private String nickname;

    @Builder
    public Member(String socialId, String socialType, String nickname) {
        this.socialId = socialId;
        this.socialType = socialType;
        this.nickname = nickname;
    }
}
