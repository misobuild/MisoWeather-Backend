package com.misobuild.service;

import com.misobuild.auth.JwtTokenProvider;
import com.misobuild.auth.KakaoOAuth;
import com.misobuild.constants.BigScaleEnum;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.constants.RegionEnum;
import com.misobuild.domain.comment.Comment;
import com.misobuild.domain.comment.CommentRepository;
import com.misobuild.domain.member.Member;
import com.misobuild.domain.member.MemberRepository;
import com.misobuild.domain.member_region_mapping.MemberRegionMapping;
import com.misobuild.domain.member_region_mapping.MemberRegionMappingRepository;
import com.misobuild.domain.member_survey_mapping.MemberSurveyMapping;
import com.misobuild.domain.member_survey_mapping.MemberSurveyMappingRepository;
import com.misobuild.domain.nickname.*;
import com.misobuild.domain.region.Region;
import com.misobuild.domain.region.RegionRepository;
import com.misobuild.dto.request.member.DeleteMemberRequestDto;
import com.misobuild.dto.request.member.LoginRequestDto;
import com.misobuild.dto.request.member.SignUpRequestDto;
import com.misobuild.dto.response.member.MemberInfoResponseDto;
import com.misobuild.dto.response.member.NicknameResponseDto;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.factory.ValidatorFactory;
import com.misobuild.utils.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    // TODO 필드 생성자 추천하지 않음
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final MemberSurveyMappingRepository memberSurveyMappingRepository;
    private final RegionRepository regionRepository;
    private final AdjectiveRepository adjectiveRepository;
    private final AdverbRepository adverbRepository;
    private final EmojiRepository emojiRepository;
    private final MemberRegionMappingRepository memberRegionMappingRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoOAuth kakaoOAuth;

    public NicknameResponseDto buildNickname() {
        // TODO RANDOM sql 사용하면 좋을 것 같다.
        Adjective adjective = adjectiveRepository.findById(getRandomId(adjectiveRepository.count()))
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        Adverb adverb = adverbRepository.findById(getRandomId(adverbRepository.count()))
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        Emoji emoji = emojiRepository.findById(getRandomId(emojiRepository.count()))
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return NicknameResponseDto.builder()
                .nickname(adjective.getWord() + " " + adverb.getWord() + emoji.getWord())
                .emoji(emoji.getEmoji())
                .build();
    }

    @Transactional
    public Member registerMember(SignUpRequestDto signUpRequestDto, String socialToken) throws ParseException {
        // TODO 애플의 경우 RSA 체크가 빠져있다.
        Validator validator = ValidatorFactory
                .of(signUpRequestDto.getSocialId(), signUpRequestDto.getSocialType(), socialToken);
        if(!validator.valid()) throw new ApiCustomException(HttpStatusEnum.BAD_REQUEST);

        memberRepository.findBySocialIdAndSocialType(signUpRequestDto.getSocialId(), signUpRequestDto.getSocialType())
                .ifPresent(m -> { throw new ApiCustomException(HttpStatusEnum.CONFLICT); });
        memberRepository.findByNickname(signUpRequestDto.getNickname())
                .ifPresent(m -> { throw new ApiCustomException(HttpStatusEnum.CONFLICT); });

        Member member = Member.builder()
                .socialId(signUpRequestDto.getSocialId())
                .socialType(signUpRequestDto.getSocialType())
                .emoji(signUpRequestDto.getEmoji())
                .nickname(signUpRequestDto.getNickname())
                .build();

        Region defaultRegion = regionRepository.findById(signUpRequestDto.getDefaultRegionId())
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        MemberRegionMapping memberRegionMapping = MemberRegionMapping.builder()
                .regionStatus(RegionEnum.DEFAULT)
                .member(member)
                .region(defaultRegion)
                .build();

        Member registeredMember = memberRepository.save(member);
        memberRegionMappingRepository.save(memberRegionMapping);

        return registeredMember;
    }

    public String reissue(LoginRequestDto loginRequestDto, String socialToken) {
        Validator validator = ValidatorFactory
                .of(loginRequestDto.getSocialId(), loginRequestDto.getSocialType(), socialToken);
        Boolean temp = validator.valid();
        System.out.println(temp);
        if(temp.equals(Boolean.FALSE)) {
            throw new ApiCustomException(HttpStatusEnum.BAD_REQUEST);
        }

        Member member = memberRepository
                .findBySocialIdAndSocialType(loginRequestDto.getSocialId(), loginRequestDto.getSocialType())
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return jwtTokenProvider
                .createToken(Long.toString(member.getMemberId()), member.getSocialId(), member.getSocialType());
    }

    public void delete(DeleteMemberRequestDto deleteMemberRequestDto) {
        Member member = memberRepository
                .findBySocialIdAndSocialType(deleteMemberRequestDto.getSocialId(), deleteMemberRequestDto.getSocialType())
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        // TODO 똑같이 member로 찾는데 명명법이 다르다.
        List<MemberRegionMapping> memberRegionMappingList = memberRegionMappingRepository.findMemberRegionMappingByMember(member);
        List<MemberSurveyMapping> memberSurveyMappingList = memberSurveyMappingRepository.findByMember(member);
        List<Comment> commentList = commentRepository.findByMember(member);

        commentRepository.deleteAll(commentList);
        memberSurveyMappingRepository.deleteAll(memberSurveyMappingList);
        memberRegionMappingRepository.deleteAll(memberRegionMappingList);
        memberRepository.delete(member);
    }

    public MemberInfoResponseDto getMemberInfo(Member member) {
        List<MemberRegionMapping> memberRegionMappingList =
                memberRegionMappingRepository.findMemberRegionMappingByMember(member);

        MemberRegionMapping memberRegionMapping = memberRegionMappingList.stream()
                .filter(item -> item.getRegionStatus().equals(RegionEnum.DEFAULT))
                .findFirst()
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return MemberInfoResponseDto.builder()
                .emoji(member.getEmoji())
                .nickname(member.getNickname())
                .regionId(memberRegionMapping.getRegion().getId())
                .regionName(BigScaleEnum
                        .getEnum(memberRegionMapping.getRegion().getBigScale()).toString())
                .build();
    }

    public Long getRandomId(Long number) {
        int randomNumber = ThreadLocalRandom
                .current()
                .nextInt(1, Long.valueOf(number).intValue() + 1);

        return Long.valueOf(randomNumber);
    }

    public Boolean ifMemberExist(String socialId, String socialType) {
        return memberRepository.findBySocialIdAndSocialType(socialId, socialType).isPresent();
    }
}
