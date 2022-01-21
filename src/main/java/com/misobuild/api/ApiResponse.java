package com.misobuild.api;

import com.misobuild.constants.HttpStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {

    @ApiModelProperty(example = "OK")
    private HttpStatusEnum status;

    @ApiModelProperty(example = "행복하세요")
    private String message;
}