package com.misobuild.api;

import lombok.Getter;

@Getter
public enum HttpStatusEnum {

    OK(200, "OK"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    CONFLICT(409, "CONFLICT"),
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private final int statusCode;
    private final String message;

    HttpStatusEnum(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}