package com.misobuild.controller;


import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"로그인"})
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/member/signup")
    public void registerUser(@RequestBody SignUpRequestDto requestDto) {
        memberService.registerMember(requestDto);
    }

    @ApiOperation(value= "로그인")
    @PostMapping("/member/login")
    public String login(@RequestBody SignUpRequestDto requestDto) {
        return memberService.login(requestDto);
    }
}
