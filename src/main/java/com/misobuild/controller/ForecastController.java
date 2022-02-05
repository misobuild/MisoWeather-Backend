package com.misobuild.controller;

import com.misobuild.api.ApiResponseWithData;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.dto.response.weather.*;
import com.misobuild.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;


@Api(tags = {"예보 (미세먼지/중기예보 추가 예정)"})
@RequiredArgsConstructor
@RestController
public class ForecastController {

    private final WeatherService weatherService;

    // OpenWeatherMap api 조회
    @ApiOperation(value = "현재 날씨")
    @GetMapping("/api/new-forecast/{regionId}")
    public ResponseEntity<ApiResponseWithData<CurrentWeatherDto>>
    getCurrentWeather(@PathVariable Long regionId) throws IOException {
        weatherService.update(regionId);
        return ResponseEntity.ok(ApiResponseWithData.<CurrentWeatherDto>builder()
                .status(HttpStatusEnum.OK)
                .data(weatherService.getCurrentWeather(regionId))
                .message("생성 성공")
                .build());
    }

    // 오픈웨더
    @ApiOperation(value = "시간 별 예보")
    @GetMapping("/api/new-forecast/hourly/{regionId}")
    public ResponseEntity<ApiResponseWithData<HourlyForecastDto>>
    getHourlyForecast(@PathVariable Long regionId) {
        return ResponseEntity.ok(ApiResponseWithData.<HourlyForecastDto>builder()
                .status(HttpStatusEnum.OK)
                .data(weatherService.getHourlyForecast(regionId))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "일 별 예보")
    @GetMapping("/api/new-forecast/daily/{regionId}")
    public ResponseEntity<ApiResponseWithData<DailyForecastDto>>
    getDailyForecast(@PathVariable Long regionId) {
        return ResponseEntity.ok(ApiResponseWithData.<DailyForecastDto>builder()
                .status(HttpStatusEnum.OK)
                .data(weatherService.getDailyForecast(regionId))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "미세먼지")
    @GetMapping("/api/new-forecast/airdust/{regionId}")
    public ResponseEntity<ApiResponseWithData<CurrentAirDto>>
    getAirDust(@PathVariable Long regionId) throws URISyntaxException {
        return ResponseEntity.ok(ApiResponseWithData.<CurrentAirDto>builder()
                .status(HttpStatusEnum.OK)
                .message("생성 성공")
                .data(weatherService.getAirDust(regionId))
                .build());
    }
}
