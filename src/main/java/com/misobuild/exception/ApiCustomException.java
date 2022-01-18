package com.misobuild.api;

import lombok.Getter;

@Getter
public class ApiCustomException extends RuntimeException {

    private final HttpStatusEnum httpStatusEnum;

    public ApiCustomException(HttpStatusEnum httpStatusEnum){
        super(httpStatusEnum.getMessage());
        this.httpStatusEnum = httpStatusEnum;
    }
}