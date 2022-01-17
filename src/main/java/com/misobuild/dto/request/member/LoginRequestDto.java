package com.misobuild.dto.request.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @ApiModelProperty(example = "qwerhosadfa2314h2314g2bejqwhr;eqwbqwefh!*(^#@1")
    private String accessToken;

    @ApiModelProperty(example = "2063494098")
    private String socialId;

    @ApiModelProperty(example = "kakao")
    private String socialType;
}
