package com.misobuild.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misobuild.constants.WeatherEnum;
import com.misobuild.domain.Timestamped;
import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "DAILY_FORECAST_TB")
public class DailyForecast extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAILY_FORECAST_SEQ")
    @JsonIgnore
    private Long seq;

    @Column(name = "FORECAST_TIME", nullable = false)
    private LocalDateTime forecastTime;

    @Column(name = "MIN_TEMPERATURE", nullable = false)
    private float minTemperature;

    @Column(name = "MAX_TEMPERATURE", nullable = false)
    private float maxTemperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "REGION_ID")
    @JsonIgnore
    private Region region;

    @Column(name = "WEATHER", nullable = false)
    private String weather;

    @Column(name = "POP", nullable = false)
    private int pop;

    @Column(name = "RAIN", nullable = false)
    private int rain;

    @Column(name = "SNOW", nullable = false)
    private int snow;

    @Column(name = "POP_ICON", nullable = false)
    private String popIcon;

    @Builder
    public DailyForecast(LocalDateTime forecastTime, float minTemperature, float maxTemperature, Region region, String weather, int pop, int rain, int snow) {
        this.forecastTime = forecastTime;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.region = region;
        this.weather = weather;
        this.pop = pop;
        this.rain = rain;
        this.snow = snow;
        this.popIcon = setPopIcon(rain, snow ,pop);
    }

    public String setPopIcon(int rain, int snow, int pop) {
        if(pop != 0){
            if(snow > 60 || rain > 60) return WeatherEnum.UMBRELLAWITHRAIN.getWeather();
            return WeatherEnum.UMBRELLA.getWeather();
        }
        return WeatherEnum.FOLDEDUMBRELLA.getWeather();
    }
}