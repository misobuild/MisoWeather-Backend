package com.misobuild.controller;


import com.misobuild.api.ApiResponse;
import com.misobuild.api.ApiResponseWithData;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.auth.JwtTokenProvider;
import com.misobuild.auth.UserDetailsImpl;
import com.misobuild.domain.member.Member;
import com.misobuild.domain.member.MemberRepository;
import com.misobuild.dto.request.member.DeleteMemberRequestDto;
import com.misobuild.dto.request.member.LoginRequestDto;
import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.dto.response.member.MemberInfoResponseDto;
import com.misobuild.dto.response.member.NicknameResponseDto;
import com.misobuild.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Api(tags = {"멤버"})
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원 가입")
    @PostMapping("api/member")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpRequestDto signUpRequestDto
            , @RequestParam String socialToken) throws ParseException {
        Member registeredMember = memberService.registerMember(signUpRequestDto, socialToken);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("serverToken", jwtTokenProvider
                .createToken(Long.toString(registeredMember.getMemberId()),
                        registeredMember.getSocialId(),
                        registeredMember.getSocialType()));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new ApiResponse(HttpStatusEnum.OK, "Register Successful"));
    }

    @ApiOperation(value= "회원 정보 가져오기")
    @GetMapping("api/member")
    public ResponseEntity<ApiResponseWithData<MemberInfoResponseDto>> getMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponseWithData.<MemberInfoResponseDto>builder()
                .status(HttpStatusEnum.OK)
                .message("멤버 정보")
                .data(memberService.getMemberInfo(userDetails.getMember()))
                .build());
    }

    // TODO loginREquestDTO 상속해서 아래 login에서 polymorphism 적용하기
    @ApiOperation(value= "미소웨더 토큰 재발급")
    @PostMapping("api/member/token")
    public ResponseEntity<ApiResponse> reissue(@RequestBody LoginRequestDto loginRequestDto
            , @RequestParam String socialToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("serverToken", memberService.reissue(loginRequestDto, socialToken));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new ApiResponse(HttpStatusEnum.OK, "Token Generated"));
    }

    @ApiOperation(value= "사용 가능 닉네임 조회하기")
    @GetMapping("api/member/nickname")
    public ResponseEntity<ApiResponseWithData<NicknameResponseDto>> buildNickname() {
        return ResponseEntity.ok(ApiResponseWithData.<NicknameResponseDto>builder()
                .status(HttpStatusEnum.OK)
                .message("생성된 닉네임과 해당 emoji")
                .data(memberService.buildNickname())
                .build());
    }

    @ApiOperation(value= "회원 삭제")
    @DeleteMapping("api/member")
    public ResponseEntity<ApiResponse> deleteMember(@RequestBody DeleteMemberRequestDto deleteMemberRequestDto) {
        memberService.delete(deleteMemberRequestDto);

        return ResponseEntity.ok()
                .body(new ApiResponse(HttpStatusEnum.OK, "Deletetion Successful"));
    }

    @ApiOperation(value="회원 가입 여부")
    @GetMapping("api/member/existence")
    public ResponseEntity<ApiResponseWithData<Boolean>> checkExistence(@RequestParam String socialId, @RequestParam String socialType){
        return ResponseEntity.ok(ApiResponseWithData.<Boolean>builder()
                .status(HttpStatusEnum.OK)
                .message("ifExist: Boolean.TRUE")
                .data(memberService.ifMemberExist(socialId, socialType))
                .build());
    }
}