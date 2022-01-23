package com.misobuild.domain.member_survey_mapping;

import com.misobuild.domain.member.Member;
import com.misobuild.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberSurveyMappingRepository extends JpaRepository<MemberSurveyMapping, Long> {
    // Optional 리턴이 안 되어서 폐기
//    @Query("select msm from MEMBER_SURVEY_MAPPING_TB msm where msm.member.memberId =: memberId and msm.survey.id = :surveyId")
//    MemberSurveyMapping findByMemberAndSurvey(@Param("memberId")Long memberId, @Param("surveyId") Long surveyId);

    List<MemberSurveyMapping> findByMemberAndSurvey(Member member, Survey survey);
    List<MemberSurveyMapping> findByMember(Member member);
    List<MemberSurveyMapping> findByCreatedAtAfter(LocalDateTime localDateTime);
}