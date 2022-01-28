package com.misobuild.dto.response.weather;

import lombok.Getter;

import java.util.Map;

@Getter
public class ForecastNowInfo {
    private String temperatureMax;
    private String temperatureMin;

    public ForecastNowInfo(Map<String, String> temperatureMap) {
        this.temperatureMax = temperatureMap.get("tmx");
        this.temperatureMin = temperatureMap.get("tmn");
    }
}
