package com.misobuild.exception;

public class ApiRequestException extends IllegalArgumentException {

    public ApiRequestException(String message) {
        super(message);
    }
}