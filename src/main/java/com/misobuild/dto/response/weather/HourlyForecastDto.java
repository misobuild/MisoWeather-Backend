package com.misobuild.dto.response.weather;

import com.misobuild.domain.weather.HourlyForecast;
import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class HourlyForecastDto {
    private final Region region;
    private final List<HourlyForecast> hourlyForecastList;

    @Builder
    public HourlyForecastDto(List<HourlyForecast> hourlyForecastList, Region region) {
        HourlyForecast currentHourForecast = hourlyForecastList.get(0);

        this.hourlyForecastList= hourlyForecastList;
        this.region = region;
    }

    public static HourlyForecastDto of(List<HourlyForecast> hourlyForecastList, Region region){
        return HourlyForecastDto.builder()
                .hourlyForecastList(hourlyForecastList)
                .region(region)
                .build();
    }
}
