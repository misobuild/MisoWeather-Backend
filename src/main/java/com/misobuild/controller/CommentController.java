package com.misobuild.controller;

import com.misobuild.api.ApiResponseWithData;
import com.misobuild.auth.UserDetailsImpl;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.dto.request.comment.CommentRegisterRequestDto;
import com.misobuild.dto.response.comment.CommentListResponseDto;
import com.misobuild.dto.response.comment.CommentRegisterResponseDto;
import com.misobuild.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"한줄평"})
@RestController
@RequiredArgsConstructor
public class CommentController {
    private static final Integer DEFAULT_SIZE = 21;
    private final CommentService commentService;

    @ApiOperation(value = "코멘트 등록")
    @PostMapping("/api/comment")
    public ResponseEntity<ApiResponseWithData<CommentRegisterResponseDto>>
    registerComment(@RequestBody CommentRegisterRequestDto commentRegisterRequestDto,
                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(ApiResponseWithData.<CommentRegisterResponseDto>builder()
                .status(HttpStatusEnum.OK)
                .data(commentService.registerComment(commentRegisterRequestDto, userDetails.getMember()))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "코멘트 조회")
    @GetMapping("/api/comment")
    public ResponseEntity<ApiResponseWithData<CommentListResponseDto>>
    getCommentList(@RequestParam(required = false) Long commentId, @RequestParam Integer size) {
        if (size == null) size = DEFAULT_SIZE;
        return ResponseEntity.ok(ApiResponseWithData.<CommentListResponseDto>builder()
                .status(HttpStatusEnum.OK)
                .data(commentService.getCommentList(commentId, PageRequest.of(0, size)))
                .message("생성 성공")
                .build());
    }
}
