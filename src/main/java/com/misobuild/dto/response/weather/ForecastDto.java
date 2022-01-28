package com.misobuild.dto.response.weather;

import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ForecastDto {
    private Region region;
    private List<ForecastByTimeDto> forecastByTime;
    private ForecastInfo forecastInfo;

    @Builder
    public ForecastDto(Region region, List<ForecastByTimeDto> forecastByTimeDtoList, ForecastInfo forecastInfo) {
        this.region = region;
        this.forecastByTime = forecastByTimeDtoList;
        this.forecastInfo = forecastInfo;
    }
}