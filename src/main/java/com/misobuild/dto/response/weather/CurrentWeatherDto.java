package com.misobuild.dto.response.weather;

import com.misobuild.domain.weather.CurrentWeather;
import com.misobuild.domain.weather.DailyForecast;
import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CurrentWeatherDto {
    private final float temperature;
    private final float temperatureMin;
    private final float temperatureMax;
    private final Region region;
    private final String weather;
    private final float windSpeed;
    private final String windSpeedIcon;
    private final int humidity;
    private final String humidityIcon;
    private final String windSpeedComment;

    @Builder
    public CurrentWeatherDto(float temperature, float temperatureMin, float temperatureMax, Region region, String weather, float windSpeed, int humidity) {
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.region = region;
        this.weather = weather;
        this.windSpeed = windSpeed;
        this.windSpeedIcon = setWindSpeedIcon(windSpeed);
        this.humidity = humidity;
        this.humidityIcon = setHumidityIcon(humidity);
        this.windSpeedComment = setWindSpeedComment(windSpeed);
    }

    public static CurrentWeatherDto fromEntity(CurrentWeather currentWeather, DailyForecast dailyForecast) {
        return CurrentWeatherDto.builder()
                .region(currentWeather.getRegion())
                .temperature(currentWeather.getTemperature())
                .temperatureMax(dailyForecast.getMaxTemperature())
                .temperatureMin(dailyForecast.getMinTemperature())
                .weather(currentWeather.getWeather())
                .windSpeed(currentWeather.getWindSpeed())
                .humidity(currentWeather.getHumidity())
                .build();
    }

    public String setHumidityIcon(int humidity) {
        if(humidity < 60) return "💧";
        else return "\uD83D\uDCA6";
    }

    public String setWindSpeedIcon(float windSpeed) {
        if(windSpeed < 5) return "\uD83C\uDF43";
        else if(windSpeed < 10) return "\uD83C\uDF2C";
        else if(windSpeed < 17) return "\uD83D\uDCA8";
        else return "\uD83C\uDF2A";
    }

    public String setWindSpeedComment(float windSpeed) {
        if(windSpeed < 5) return "약해요";
        else if(windSpeed < 10) return "약간 강해요";
        else if(windSpeed < 17) return "강해요";
        else return "매우 강해요";
    }
}
