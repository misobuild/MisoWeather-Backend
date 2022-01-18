package com.misobuild.controller;

import com.misobuild.api.ApiResponseWithData;
import com.misobuild.api.HttpStatusEnum;
import com.misobuild.dto.response.weather.ForecastDto;
import com.misobuild.dto.response.weather.ForecastNowDto;
import com.misobuild.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Api(tags = {"예보"})
@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    // 기상청 api 조회
    @ApiOperation(value = "단기예보 간략 정보")
    @GetMapping("/api/forecast/{regionId}")
    public ResponseEntity<ApiResponseWithData<ForecastNowDto>>
    getForecastNow(@PathVariable Long regionId) throws IOException {
        return ResponseEntity.ok(ApiResponseWithData.<ForecastNowDto>builder()
                .status(HttpStatusEnum.OK)
                .data(weatherService.getForecastNow(regionId))
                .message("생성 성공")
                .build());
    }

    // 기상청 api 조회
    @ApiOperation(value = "단기예보 세부 정보")
    @GetMapping("/api/forecast/{regionId}/detail")
    public ResponseEntity<ApiResponseWithData<ForecastDto>>
    getForecastDetail(@PathVariable Long regionId) throws IOException {
        return ResponseEntity.ok(ApiResponseWithData.<ForecastDto>builder()
                .status(HttpStatusEnum.OK)
                .data(weatherService.getForecastDetail(regionId))
                .message("생성 성공")
                .build());
    }

    // 기상청 api 조회
    @ApiOperation(value = "중기예보")
    @GetMapping("/api/forecast/{regionId}/midterm")
    public ResponseEntity<ApiResponseWithData<ForecastDto>>
    getMidForecast(@PathVariable Long regionId) throws IOException {
        return ResponseEntity.ok(ApiResponseWithData.<ForecastDto>builder()
                .status(HttpStatusEnum.OK)
                .data(weatherService.getForecastDetail(regionId))
                .message("생성 성공")
                .build());
    }


}
