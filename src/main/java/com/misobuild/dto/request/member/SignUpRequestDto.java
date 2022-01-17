package com.misobuild.dto.request.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    @ApiModelProperty(example = "2063494098")
    private String socialId;

    @ApiModelProperty(example = "kakao")
    private String socialType;

    @ApiModelProperty(example = "희망찬 아기기름램프")
    private String nickname;

    @ApiModelProperty(example = "\uD83E\uDE94")
    private String emoji;

    @ApiModelProperty(example = "fW1O-o8xirZrWZj_9_1iVvGvxsnRvCww3hIjnQopcFAAAAF-YhKhXQ")
    private String accessToken;

    @ApiModelProperty(example = "1241")
    private Long defaultRegionId;
}
