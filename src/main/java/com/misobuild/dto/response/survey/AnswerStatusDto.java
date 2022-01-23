package com.misobuild.dto.response.survey;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerStatusDto {
    private Long surveyId;
    private String memberAnswer;
    private Boolean answered = Boolean.FALSE;


    public AnswerStatusDto(Long surveyId, String memberAnswer) {
        this.surveyId = surveyId;
        this.memberAnswer = memberAnswer;
        this.answered = Boolean.TRUE;
    }

    @Builder
    public AnswerStatusDto(Long surveyId, String memberAnswer, Boolean answered) {
        this.surveyId = surveyId;
        this.memberAnswer = memberAnswer;
        this.answered = answered;
    }
}
