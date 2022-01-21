package com.misobuild.api;

import com.misobuild.constants.HttpStatusEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiResponseWithData<T> extends ApiResponse{
    private final T data;

    @Builder
    public ApiResponseWithData(HttpStatusEnum status, String message, T data) {
        super(status, message);
        this.data = data;
    }
}
