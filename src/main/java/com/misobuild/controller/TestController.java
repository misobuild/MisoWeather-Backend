package com.misobuild.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"테스트 도메인"})
@RequiredArgsConstructor
@RestController
public class TestController {
    //북마크 함
    @ApiOperation(value = "Returns number")
    @PostMapping("/api/test/")
    public ResponseEntity testPostMapping(@PathVariable Long number){
        HttpHeaders headers = new HttpHeaders();
        headers.set("123123", "12312312");

        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(number);
    }

    // 북마크한 노트들 중에서 노트 제목 검색
    @ApiOperation(value = "Returns input string")
    @GetMapping("/api/test/{input}")
    public ResponseEntity testGetMapping(@PathVariable String input){
        HttpHeaders headers = new HttpHeaders();
        headers.set("123123", "12312312");

        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(input);
    }
}
