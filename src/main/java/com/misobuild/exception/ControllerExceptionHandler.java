package com.misobuild.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ApiCustomException.class)
    public ResponseEntity<Error> exception(ApiCustomException exception){
        return new ResponseEntity<>(Error.create(exception.getHttpStatusEnum()), HttpStatus.OK);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error{
        private int httpStatus;
        private String message;

        static Error create(HttpStatusEnum exception){
            return new ControllerExceptionHandler.Error(exception.getStatusCode(), exception.getMessage());
        }
    }
}