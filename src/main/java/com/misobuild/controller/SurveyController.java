package com.misobuild.controller;

import com.misobuild.api.ApiResponseWithData;
import com.misobuild.auth.UserDetailsImpl;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.dto.request.survey.AnswerSurveyDto;
import com.misobuild.dto.response.ListDto;
import com.misobuild.dto.response.survey.AnswerStatusDto;
import com.misobuild.dto.response.survey.AnswerSurveyJoinDto;
import com.misobuild.dto.response.survey.AnswerSurveyResponseDto;
import com.misobuild.service.SurveyService;
import com.misobuild.utils.reader.SurveyReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"서베이"})
@RestController
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @ApiOperation(value = "사용자의 서베이 답변 상태 가져오기")
    @GetMapping("/api/survey/member")
    public ResponseEntity<ApiResponseWithData<ListDto<AnswerStatusDto>>>
    getSurveyStatus(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponseWithData.<ListDto<AnswerStatusDto>>builder()
                .status(HttpStatusEnum.OK)
                .data(surveyService.getAnswerStatus(userDetails.getMember()))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "서베이 답변 목록 가져오기")
    @GetMapping("/api/survey/answers/{surveyId}")
    public ResponseEntity<ApiResponseWithData<ListDto<AnswerSurveyJoinDto>>>
    getAnswer(@PathVariable Long surveyId) {
        return ResponseEntity.ok(ApiResponseWithData.<ListDto<AnswerSurveyJoinDto>>builder()
                .status(HttpStatusEnum.OK)
                .data(surveyService.getAnswerList(surveyId))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "서베이 답변 입력")
    @PostMapping("/api/survey")
    public ResponseEntity<ApiResponseWithData<AnswerSurveyResponseDto>>
    registerComment(@RequestBody AnswerSurveyDto answerSurveyDto,
                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponseWithData.<AnswerSurveyResponseDto>builder()
                .status(HttpStatusEnum.OK)
                .data(surveyService.answerSurvey(userDetails.getMember(), answerSurveyDto))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "서베이 결과")
    @GetMapping("/api/survey")
    public ResponseEntity<ApiResponseWithData<ListDto<SurveyReader>>>
    getSurveryResult(@RequestParam(required = false) String shortBigScale) {
        return ResponseEntity.ok(ApiResponseWithData.<ListDto<SurveyReader>>builder()
                .status(HttpStatusEnum.OK)
                .data(surveyService.getSurveyResultList(shortBigScale))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "서베이 답변 여부")
    @GetMapping("/api/survey/precheck")
    public ResponseEntity<ApiResponseWithData<Boolean>>
    checkSurveyByMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponseWithData.<Boolean>builder()
                .status(HttpStatusEnum.OK)
                .data(surveyService.ifAnswerExist(userDetails.getMember()))
                .message("생성 성공")
                .build());
    }
}
