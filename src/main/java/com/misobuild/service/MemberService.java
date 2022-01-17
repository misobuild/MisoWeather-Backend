package com.misobuild.service;

import com.misobuild.api.ApiException;
import com.misobuild.api.HttpStatusEnum;
import com.misobuild.auth.JwtTokenProvider;
import com.misobuild.auth.KakaoOAuth;
import com.misobuild.domain.member.Member;
import com.misobuild.domain.member.MemberRepository;
import com.misobuild.domain.member_region_mapping.MemberRegionMapping;
import com.misobuild.domain.member_region_mapping.MemberRegionMappingRepository;
import com.misobuild.domain.member_region_mapping.RegionStatus;
import com.misobuild.domain.nickname.*;
import com.misobuild.domain.region.Region;
import com.misobuild.domain.region.RegionRepository;
import com.misobuild.dto.request.member.LoginRequestDto;
import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.dto.response.member.NicknameResponseDto;
import com.misobuild.utils.IdCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    // TODO 필드 생성자 추천하지 않음
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;
    private final AdjectiveRepository adjectiveRepository;
    private final AdverbRepository adverbRepository;
    private final EmojiRepository emojiRepository;
    private final MemberRegionMappingRepository memberRegionMappingRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuth kakaoOAuth;
    private final IdCreator idCreator;

    public NicknameResponseDto buildNickname() {
        Adjective adjective = adjectiveRepository
                .findById(idCreator.getRandomId(adjectiveRepository.count()))
                .orElseThrow(() -> new ApiException(HttpStatusEnum.NOT_FOUND));

        Adverb adverb = adverbRepository
                .findById(idCreator.getRandomId(adverbRepository.count()))
                .orElseThrow(() -> new ApiException(HttpStatusEnum.NOT_FOUND));

        // TODO 이모지 id 값 사라진 것들 있어 에러 발생 가능성 있으니 디비 목록 손봐야 한다.
        Emoji emoji = emojiRepository
                .findById(idCreator.getRandomId(emojiRepository.count()))
                .orElseThrow(() -> new ApiException(HttpStatusEnum.NOT_FOUND));

        StringBuilder nickname = new StringBuilder();
        nickname.append(adjective.getWord())
                .append(" ")
                .append(adverb.getWord())
                .append(emoji.getWord());

        return NicknameResponseDto.builder()
                .nickname(nickname.toString())
                .emoji(emoji.getEmoji())
                .build();
    }

    @Transactional
    public void registerMember(SignUpRequestDto signUpRequestDto) {
        String accessToken = signUpRequestDto.getAccessToken();

        // TODO 애플 로그인과 카카오 로그인에 다형성 리팩토링 필요
        if(!kakaoOAuth.getUserId(accessToken).equals(Long.valueOf(signUpRequestDto.getSocialId()))){
            throw new ApiException(HttpStatusEnum.BAD_REQUEST);
        }

        memberRepository.findBySocialIdAndSocialType(signUpRequestDto.getSocialId(), signUpRequestDto.getSocialType())
                .ifPresent( m -> { throw new ApiException(HttpStatusEnum.CONFLICT); });
        memberRepository.findByNickname(signUpRequestDto.getNickname())
                .ifPresent( m -> { throw new ApiException(HttpStatusEnum.CONFLICT); });

        Member member = Member.builder()
                .socialId(signUpRequestDto.getSocialId())
                .socialType(signUpRequestDto.getSocialType())
                .emoji(signUpRequestDto.getEmoji())
                .nickname(signUpRequestDto.getNickname())
                .build();

        Region defaultRegion = regionRepository.findById(signUpRequestDto.getDefaultRegionId())
                .orElseThrow(() -> new ApiException(HttpStatusEnum.NOT_FOUND));

        MemberRegionMapping memberRegionMapping = MemberRegionMapping.builder()
                .regionStatus(RegionStatus.DEFAULT)
                .member(member)
                .region(defaultRegion)
                .build();

        memberRepository.save(member);
        memberRegionMappingRepository.save(memberRegionMapping);
    }

    public String login(LoginRequestDto loginRequestDto) {
        if(!kakaoOAuth
                .getUserId(loginRequestDto.getAccessToken())
                .equals(Long.valueOf(loginRequestDto.getSocialId()))){
            throw new ApiException(HttpStatusEnum.BAD_REQUEST);
        }

        Member member = memberRepository
                .findBySocialIdAndSocialType(loginRequestDto.getSocialId(), loginRequestDto.getSocialType())
                .orElseThrow(() -> new ApiException(HttpStatusEnum.NOT_FOUND));

        return jwtTokenProvider
                .createToken(Long.toString(member.getMemberId()), member.getSocialId(), member.getSocialType());
    }
}
