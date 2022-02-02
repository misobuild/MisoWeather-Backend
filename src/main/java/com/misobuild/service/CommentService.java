package com.misobuild.service;

import com.misobuild.constants.BigScaleEnum;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.constants.RegionEnum;
import com.misobuild.domain.comment.Comment;
import com.misobuild.domain.comment.CommentRepository;
import com.misobuild.domain.member.Member;
import com.misobuild.domain.member_region_mapping.MemberRegionMappingRepository;
import com.misobuild.dto.request.comment.CommentRegisterRequestDto;
import com.misobuild.dto.response.comment.CommentListResponseDto;
import com.misobuild.dto.response.comment.CommentRegisterResponseDto;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.reader.CommentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private static final int DEFAULT_SIZE = 21;

    private final CommentRepository commentRepository;
    private final MemberRegionMappingRepository memberRegionMappingRepository;
    private final CommentReader commentReader;

    public CommentRegisterResponseDto registerComment(CommentRegisterRequestDto commentRegisterRequestDto, Member member) {
        String bigScale = memberRegionMappingRepository.findMemberRegionMappingByMember(member).stream()
                .filter(item -> item.getRegionStatus().equals(RegionEnum.DEFAULT))
                .map(item -> item.getRegion().getBigScale())
                .findFirst()
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        Comment comment = Comment.builder()
                .content(commentReader.checker(commentRegisterRequestDto.getContent()))
                .bigScale(BigScaleEnum.getEnum(bigScale).toString())
                .member(member)
                .nickname(member.getNickname())
                .deleted(Boolean.FALSE)
                .emoji(member.getEmoji())
                .build();

        commentRepository.save(comment);

        return CommentRegisterResponseDto.builder()
                .commentList(commentRepository.findAll())
                .build();
    }

    public CommentListResponseDto getCommentList(Long commentId, Pageable page){
        final List<Comment> rawCommentList = getComments(commentId, page);
        final Long lastIdOfList = rawCommentList.isEmpty() ?
                null : rawCommentList.get(rawCommentList.size() - 1).getId();

        return new CommentListResponseDto(rawCommentList, hasNext(lastIdOfList));
    }

    private List<Comment> getComments(Long commentId, Pageable page) {
        return commentId == null ?
                this.commentRepository.findAllByOrderByIdDesc(page) :
                this.commentRepository.findByIdLessThanOrderByIdDesc(commentId, page);
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return this.commentRepository.existsByIdLessThan(id);
    }

}
