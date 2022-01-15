package com.misobuild.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {

    @ApiModelProperty(example = "OK")
    private HttpStatusEnum httpStatus;

    @ApiModelProperty(example = "행복하세요")
    private String message;
}