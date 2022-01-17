package com.misobuild.dto.response.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NicknameResponseDto {
    private String nickname;
    private String emoji;

    @Builder
    public NicknameResponseDto(String nickname, String emoji){
        this.nickname = nickname;
        this.emoji = emoji;
    }
}