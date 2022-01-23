package com.misobuild.dto.response.survey;

import lombok.Getter;

@Getter
public class AnswerSurveyJoinDto {
    private Long answerId;
    private String answerDescription;
    private String answer;
    private Long surveyId;
    private String surveyDescription;
    private String surveyTitle;

    public AnswerSurveyJoinDto(Long answerId, String answerDescription, String answer, Long surveyId, String surveyDescription, String surveyTitle) {
        this.answerId = answerId;
        this.answerDescription = answerDescription;
        this.answer = answer;
        this.surveyId = surveyId;
        this.surveyDescription = surveyDescription;
        this.surveyTitle = surveyTitle;
    }
}
