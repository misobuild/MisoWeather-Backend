package com.misobuild.api;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatusEnum httpStatusEnum;

    public ApiException(HttpStatusEnum httpStatusEnum){
        super(httpStatusEnum.getMessage());
        this.httpStatusEnum = httpStatusEnum;
    }

}