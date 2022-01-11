package com.misobuild.dto.request.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {
    private String socialId;
    private String socialType;
}
