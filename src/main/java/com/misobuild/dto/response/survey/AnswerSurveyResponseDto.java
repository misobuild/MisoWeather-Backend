package com.misobuild.dto.response.survey;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerSurveyResponseDto {
    private String surveyDescription;
    private String answer;

    @Builder
    public AnswerSurveyResponseDto(String surveyDescription, String answer) {
        this.surveyDescription = surveyDescription;
        this.answer = answer;
    }
}
