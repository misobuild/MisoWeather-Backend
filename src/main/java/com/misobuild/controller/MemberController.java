package com.misobuild.controller;


import com.misobuild.api.ApiResponse;
import com.misobuild.api.ApiResponseWithData;
import com.misobuild.api.HttpStatusEnum;
import com.misobuild.dto.request.member.LoginRequestDto;
import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.dto.response.member.NicknameResponseDto;
import com.misobuild.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"멤버"})
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("api/member/signup")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody SignUpRequestDto signUpRequestDto) {
        memberService.registerMember(signUpRequestDto);
        return ResponseEntity.ok()
                .body(new ApiResponse(HttpStatusEnum.OK, "Register Successful"));
    }

    @ApiOperation(value= "로그인")
    @PostMapping("api/member/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequestDto loginRequestDto) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("token", memberService.login(loginRequestDto));

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(new ApiResponse(HttpStatusEnum.OK, "Token Generated"));
    }

    @ApiOperation(value= "닉네임 받기")
    @GetMapping("api/member/nickname")
    public ResponseEntity<ApiResponseWithData<NicknameResponseDto>> buildNickname() {
        return ResponseEntity.ok(ApiResponseWithData.<NicknameResponseDto>builder()
                .httpStatus(HttpStatusEnum.OK)
                .message("생성된 닉네임과 해당 emoji")
                .data(memberService.buildNickname())
                .build());
    }
}
