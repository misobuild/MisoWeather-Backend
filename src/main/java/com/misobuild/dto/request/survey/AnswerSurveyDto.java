package com.misobuild.dto.request.member.survey;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerSurveyDto {
    @NotNull
    @ApiModelProperty(example = "1")
    private Long surveyId;
    @NotNull
    @ApiModelProperty(example = "1")
    private Long answerId;
    @NotNull
    @ApiModelProperty(example = "세종")
    private String shortBigScale;
}

