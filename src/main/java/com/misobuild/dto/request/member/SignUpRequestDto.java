package com.misobuild.dto.request.member;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @NotNull
    @ApiModelProperty(example = "2063494098")
    private String socialId;

    @NotNull
    @ApiModelProperty(example = "kakao")
    private String socialType;

    @NotNull
    @ApiModelProperty(example = "희망찬 아기기름램프")
    private String nickname;

    @NotNull
    @ApiModelProperty(example = "\uD83E\uDE94")
    private String emoji;

    @NotNull
    @ApiModelProperty(example = "1241")
    private Long defaultRegionId;
}
