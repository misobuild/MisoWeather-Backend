package com.misobuild.dto.response.weather;

import com.misobuild.constants.WeatherEnum;
import com.misobuild.domain.weather.DailyForecast;
import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DailyForecastDto {
    private final Region region;
    private final List<DailyForecast> dailyForecastList;
    private final int rain;
    private final int snow;
    private final int pop;
    private final String popIcon;


    @Builder
    public DailyForecastDto(Region region, List<DailyForecast> dailyForecastList, int rain, int snow, int pop) {
        this.region = region;
        this.dailyForecastList = dailyForecastList;
        this.rain = rain;
        this.snow = snow;
        this.pop = pop;
        this.popIcon = setPopIcon(rain, snow, pop);
    }

    public static DailyForecastDto of(List<DailyForecast> dailyForecastList, Region region){
        return DailyForecastDto.builder()
                .dailyForecastList(dailyForecastList)
                .region(region)
                .build();
    }

    public String setPopIcon(int rain, int snow, int pop) {
        if(pop != 0){
            if(snow > 60 || rain > 60) return WeatherEnum.UMBRELLAWITHRAIN.getWeather();
            return WeatherEnum.UMBRELLA.getWeather();
        }
        return WeatherEnum.FOLDEDUMBRELLA.getWeather();
    }
}
