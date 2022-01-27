package com.misobuild.dto.api;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiVillageForecastDto {
    private String baseDate;
    private String baseTime;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private int nx;
    private int ny;
}
