package com.misobuild.service;

import com.misobuild.auth.JwtTokenProvider;
import com.misobuild.domain.member.Member;
import com.misobuild.domain.member.MemberRepository;
import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.exception.ApiRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void registerMember(SignUpRequestDto signUpRequestDto) {
        String socialId = signUpRequestDto.getSocialId();
        String socialType = signUpRequestDto.getSocialType();

        memberRepository.findBySocialIdAndSocialType(socialId, socialType)
                .ifPresent( m -> {
                    throw new ApiRequestException("이미 가입한 회원입니다.");
                });

        Member member = new Member(socialId, socialType, "nickname");

        memberRepository.save(member);
    }

    @Transactional
    public String login(SignUpRequestDto signUpRequestDto) {
        String socialId = signUpRequestDto.getSocialId();
        String socialType = signUpRequestDto.getSocialType();

        Member member = memberRepository
                .findBySocialIdAndSocialType(socialId, socialType)
                .orElseThrow(() -> new ApiRequestException("존재하지 않는 유저입니다."));

        return jwtTokenProvider
                .createToken(Long.toString(member.getMemberId()), member.getSocialId(), member.getSocialType());
    }
}
