package com.misobuild.api;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponseWithData<T> extends ApiResponse{
    private final T data;

    @Builder
    public ApiResponseWithData(HttpStatusEnum httpStatus, String message, T data) {
        super(httpStatus, message);
        this.data = data;
    }
}
