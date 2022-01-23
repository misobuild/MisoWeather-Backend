package com.misobuild.domain.survey;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryQueryDsl {
    //    @Query("select answer from ANSWER_TB answer where answer.survey.id = :surveyId")
//    List<Answer> findAllBySurvey(@Param("surveyId") Long surveyId);
    List<Answer> findAllBySurveyId(Long surveyId);

//    @Query("select answer from ANSWER_TB answer join fetch answer.survey where answer.survey.id = :surveyId")
//    List<AnswerSurveyJoinDto> findAnswerSurveyMapping(@Param("surveyId") Long surveyId);
}