package com.misobuild.dto.response.comment;

import com.misobuild.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentRegisterResponseDto {
    private List<Comment> commentList;

    @Builder
    public CommentRegisterResponseDto(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
