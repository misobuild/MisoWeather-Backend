package com.misobuild.service;

import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.member.Member;
import com.misobuild.domain.member_survey_mapping.MemberSurveyMapping;
import com.misobuild.domain.member_survey_mapping.MemberSurveyMappingRepository;
import com.misobuild.domain.survey.Answer;
import com.misobuild.domain.survey.AnswerRepository;
import com.misobuild.domain.survey.Survey;
import com.misobuild.domain.survey.SurveyRepository;
import com.misobuild.dto.request.survey.AnswerSurveyDto;
import com.misobuild.dto.response.ListDto;
import com.misobuild.dto.response.survey.AnswerStatusDto;
import com.misobuild.dto.response.survey.AnswerSurveyJoinDto;
import com.misobuild.dto.response.survey.AnswerSurveyResponseDto;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.reader.SurveyReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final MemberSurveyMappingRepository memberSurveyMappingRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;


    public ListDto<AnswerSurveyJoinDto> getAnswerList(Long surveyId) {
        // List<Answer> answerList = answerRepository.findAllBySurveyId(surveyId);
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));
        List<AnswerSurveyJoinDto> answerSurveyJoinList = answerRepository.findAnswerSurveyJoinBySurveyId(surveyId);
        if (answerSurveyJoinList.isEmpty()) throw new ApiCustomException(HttpStatusEnum.NOT_FOUND);
        return ListDto.<AnswerSurveyJoinDto>builder().responseList(answerSurveyJoinList).build();
    }

    public AnswerSurveyResponseDto answerSurvey(Member member, AnswerSurveyDto answerSurveyDto) {
        Answer answer = answerRepository.findById(answerSurveyDto.getAnswerId())
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));
        Survey survey = surveyRepository.findById(answerSurveyDto.getSurveyId())
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        if (!answer.getSurvey().getId().equals(survey.getId())){
            throw new ApiCustomException(HttpStatusEnum.CONFLICT);
        }

        List<MemberSurveyMapping> memberSurveyMappingList = memberSurveyMappingRepository.findByMemberAndSurvey(member, survey).stream()
                .filter(item -> item.getCreatedAt().getYear() == LocalDate.now().getYear())
                .filter(item -> item.getCreatedAt().getMonth() == LocalDate.now().getMonth())
                .filter(item -> item.getCreatedAt().getDayOfMonth() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());

        if (!memberSurveyMappingList.isEmpty()) throw new ApiCustomException(HttpStatusEnum.CONFLICT);

        MemberSurveyMapping memberSurveyMapping = MemberSurveyMapping.builder()
                .member(member).answer(answer).survey(survey)
                .shortBigScale(answerSurveyDto.getShortBigScale())
                .build();

        memberSurveyMappingRepository.save(memberSurveyMapping);
        return AnswerSurveyResponseDto.builder()
                .surveyDescription(survey.getDescription())
                .answer(answer.getAnswer())
                .build();
    }

    public ListDto<AnswerStatusDto> getAnswerStatus(Member member) {
        List<Long> surveyIdList = surveyRepository.findAll().stream()
                .map(item -> item.getId())
                .collect(Collectors.toList());
        log.info("surveyList are: {}", surveyIdList);

        // TODO jpa isAfter 지원하니 filter 필요없다. 속도는 누가 더 빠를까?
        List<AnswerStatusDto> answerStatusDtoList = memberSurveyMappingRepository.findByMember(member)
                .stream()
                .filter(item ->
                        item.getCreatedAt()
                                .isAfter(LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.of(23, 59))))
                .map(item -> {
                    // TODO db 배열 순서가 달라질 경우 리스트 인덱스가 달라지므로 문제가 생길 수 있다.
                    surveyIdList.remove(item.getSurvey().getId());
                    // TODO answer가 두 번이라니.. 필드 이름 바꾸자.
                    return new AnswerStatusDto(item.getSurvey().getId(), item.getAnswer().getAnswer());
                })
                .collect(Collectors.toList());


        // 비어있는 애들의 리스트를 가져왔으니 그걸 null 채워서 돌려줘야 한다.
        List<AnswerStatusDto> nullStatusDtoList = surveyIdList.stream()
                // TODO 생성자 하나 더 만들어서 다를 경우 수행하는 게 달라지게 한다.
                .map(item -> AnswerStatusDto.builder()
                        .surveyId(item)
                        .memberAnswer(null)
                        .answered(Boolean.FALSE).build())
                .collect(Collectors.toList());
        answerStatusDtoList.addAll(nullStatusDtoList);

        answerStatusDtoList.sort(Comparator.comparing(AnswerStatusDto::getSurveyId));

        return ListDto.<AnswerStatusDto>builder().responseList(answerStatusDtoList).build();
    }

    public ListDto<SurveyReader> getSurveyResultList(String shortBigScale) {
        List<SurveyReader> surveyReaderList = new ArrayList<>();

        // TODO 일주일 전꺼부터 되게끔 되어있다.
        List<MemberSurveyMapping> tempList = memberSurveyMappingRepository
                .findByCreatedAtAfter(LocalDateTime.of(LocalDate.now().minusDays(7L), LocalTime.of(23,59)));

        List<MemberSurveyMapping> todaySurveyList = tempList;
        if(shortBigScale != null) {
            todaySurveyList = tempList.stream()
                    .filter(item -> item.getShortBigScale().equals(shortBigScale)).collect(Collectors.toList());
        }

        List<Survey> surveyList = surveyRepository.findAll();

        for (Survey survey : surveyList) {
            List<MemberSurveyMapping> msmList = todaySurveyList.stream()
                    .filter(msm -> msm.getSurvey().getId().equals(survey.getId()))
                    .collect(Collectors.toList());
            surveyReaderList.add(SurveyReader.builder()
                    .surveyId(survey.getId())
                    .surveyTitle(survey.getTitle())
                    .surveyDescription(survey.getDescription())
                    .msmList(msmList).build());
        }

        surveyReaderList.forEach(SurveyReader::setInfoMap);
        surveyReaderList.forEach(SurveyReader::setValues);

        return ListDto.<SurveyReader>builder()
                .responseList(surveyReaderList)
                .build();
    }

    public Boolean ifAnswerExist(Member member){
        List<MemberSurveyMapping> candidateList = memberSurveyMappingRepository
                .findByCreatedAtAfter(LocalDateTime.of(LocalDate.now().minusDays(1L), LocalTime.of(23, 59)))
                .stream()
                .filter(item -> item.getMember().getMemberId().equals(member.getMemberId()))
                .collect(Collectors.toList());

        return !candidateList.isEmpty();
    }

}
