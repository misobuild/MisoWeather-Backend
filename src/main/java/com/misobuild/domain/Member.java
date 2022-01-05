package com.misobuild.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity
public class Member {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Builder
    public Member(String nickname) {
        this.nickname = nickname;
    }
}
