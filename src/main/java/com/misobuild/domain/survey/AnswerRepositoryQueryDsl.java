package com.misobuild.domain.survey;

import com.misobuild.dto.response.survey.AnswerSurveyJoinDto;

import java.util.List;

public interface AnswerRepositoryQueryDsl {
    List<AnswerSurveyJoinDto> findAnswerSurveyJoinBySurveyId(Long surveyId);
}
