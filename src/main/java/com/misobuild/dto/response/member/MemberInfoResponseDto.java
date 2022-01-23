package com.misobuild.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoResponseDto {
    private String nickname;
    private String emoji;
    private Long regionId;
    private String regionName;

    @Builder
    public MemberInfoResponseDto(String nickname, String emoji, Long regionId, String regionName){
        this.nickname = nickname;
        this.emoji = emoji;
        this.regionId = regionId;
        this.regionName = regionName;
    }
}