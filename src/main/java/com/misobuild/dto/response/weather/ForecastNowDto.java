package com.misobuild.dto.response.weather;

import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForecastNowDto {
    private Region region;
    private ForecastByTimeDto forecast;
    private ForecastNowInfo forecastInfo;

    @Builder
    public ForecastNowDto(Region region, ForecastByTimeDto forecastByTimeDto, ForecastNowInfo forecastNowInfo) {
        this.region = region;
        this.forecast = forecastByTimeDto;
        this.forecastInfo = forecastNowInfo;
    }
}