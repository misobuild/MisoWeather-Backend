package com.misobuild.dto.request.member;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @NotNull
    @ApiModelProperty(example = "2063494098")
    private String socialId;

    @NotNull
    @ApiModelProperty(example = "kakao")
    private String socialType;
}
