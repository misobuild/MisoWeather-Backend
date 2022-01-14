package com.misobuild.controller;


import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public void registerUser(@RequestBody SignUpRequestDto requestDto) {
        memberService.registerMember(requestDto);
    }

    @ApiOperation(value= "로그인")
    @PostMapping("api/member/login")
    public String login(@RequestBody SignUpRequestDto requestDto) {
        return memberService.login(requestDto);
    }
}
