package com.misobuild.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misobuild.domain.Timestamped;
import com.misobuild.domain.region.Region;
import com.misobuild.utils.factory.WeatherEnumFactory;
import com.misobuild.utils.reader.openweather.RawHourly;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "HOURLY_FORECAST_TB")
public class HourlyForecast extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HOURLY_FORECAST_SEQ")
    @JsonIgnore
    private Long seq;

    @Column(name = "FORECAST_TIME", nullable = false)
    private LocalDateTime forecastTime;

    @Column(name = "TEMPERATURE", nullable = false)
    private float temperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "REGION_ID")
    @JsonIgnore
    private Region region;

    @Column(name = "WEATHER", nullable = false)
    private String weather;


    @Builder
    public HourlyForecast(
            LocalDateTime forecastTime,
            float temperature,
            Region region,
            String weather) {
        this.forecastTime = forecastTime;
        this.temperature = temperature;
        this.region = region;
        this.weather = weather;
    }

    public static HourlyForecast of(RawHourly rawHourly, Region region, int FIRST_INDEX){
        return HourlyForecast.builder()
                .weather(WeatherEnumFactory.of(rawHourly.getWeather().get(FIRST_INDEX).getId()).getWeather())
                .forecastTime(Instant.ofEpochSecond(rawHourly.getDt()).atZone(ZoneId.systemDefault()).toLocalDateTime())
                .temperature(rawHourly.getTemp())
                .region(region)
                .build();
    }

    public HourlyForecast update(float temperature, String weather){
        this.temperature = temperature;
        this.weather = weather;
        return this;
    }
}