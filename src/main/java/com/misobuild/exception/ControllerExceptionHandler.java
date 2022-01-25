package com.misobuild.exception;

import com.misobuild.constants.HttpStatusEnum;
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
    // TODO 이거 이외에도 에러코드가 아닌 200 주는 상황이 분명 있을 것.
    public ResponseEntity<Error> exception(ApiCustomException exception){
        return new ResponseEntity<>(Error.create(exception.getHttpStatusEnum()), HttpStatus.valueOf(exception.getHttpStatusEnum().getStatusCode()));
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Error{
        private int status;
        private String message;

        static Error create(HttpStatusEnum exception){
            return new ControllerExceptionHandler.Error(exception.getStatusCode(), exception.getMessage());
        }
    }
}