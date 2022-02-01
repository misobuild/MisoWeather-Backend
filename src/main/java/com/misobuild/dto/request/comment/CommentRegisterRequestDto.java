package com.misobuild.dto.request.comment;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class CommentRegisterRequestDto {
    @NotNull
    private String content;
}

