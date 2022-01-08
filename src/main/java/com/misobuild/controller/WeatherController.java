package com.misobuild.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Api(tags = {"테스트 도메인"})
@RequiredArgsConstructor
@RestController
public class WeatherController {

    @ApiOperation(value = "Returns number")
    @PostMapping("/api/test/")
    public ResponseEntity testPostMapping(@PathVariable Long number) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("123123", "12312312");

        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(number);
    }

    @ApiOperation(value = "Returns input string")
    @GetMapping("/api/test/{input}")
    public ResponseEntity testGetMapping(@PathVariable String input) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("123123", "12312312");

        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(input);
    }

    // 기상청 api 조회
    @ApiOperation(value = "기상청 API 테스트")
    @GetMapping("/api/weather/")
    public ResponseEntity apiCall() throws IOException {
        System.out.println("hello");
        StringBuilder urlBuilder = new StringBuilder();

        LocalDateTime currentDateTime = LocalDateTime.now();

        String urlBase = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
        String serviceKey = "jh7U7E40G4%2F3siAI6e47sxtVpI2ZBk%2FnJvhYVTP1PdY7uLoeyaQKvunYbO1O5%2BEqQYDCdHddKvR7b0UWCPZHPA%3D%3D";
        int pageNo = 1;
        int numOfRows = 10;
        String dataType = "JSON";
        int nx = 60;
        int ny = 20;

        urlBuilder.append(urlBase).append("?")
                .append("serviceKey=").append(serviceKey)
                .append("&pageNo=").append(pageNo)
                .append("&numOfRows=").append(numOfRows)
                .append("&dataType=").append(dataType)
                .append("&base_date=")
                .append(LocalDate.now().getYear())
                .append(LocalDate.now().getMonth().getValue() < 10 ? "0" + currentDateTime.getMonth().getValue() : currentDateTime.getMonth().getValue())
                .append(currentDateTime.getDayOfMonth() < 10 ? "0" + (currentDateTime.getDayOfMonth()) : currentDateTime.getDayOfMonth())
                .append("&base_time=").append(currentDateTime.getHour() + "00")
                .append("&nx=").append(nx)
                .append("&ny=").append(ny);

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-type", "application/json");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        StringBuilder resultBuilder = new StringBuilder();
        String resultLine;
        while ((resultLine = br.readLine()) != null) {
            resultBuilder.append(resultLine);
        }

        urlConnection.disconnect();

        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .body(resultBuilder.toString());
    }
}
