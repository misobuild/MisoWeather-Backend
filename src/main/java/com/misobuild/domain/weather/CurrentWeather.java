package com.misobuild.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misobuild.domain.Timestamped;
import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "CURRENT_WEATHER_TB")
public class CurrentWeather extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CURRENT_WEATHER_SEQ")
    private Long seq;

    @Column(name = "TEMPERATURE", nullable = false)
    private float temperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "REGION_ID")
    private Region region;

    @Column(name = "WEATHER", nullable = false)
    private String weather;

    @JsonIgnore
    @Column(name = "WIND_SPEED", nullable = false)
    private float windSpeed;

    @JsonIgnore
    @Column(name = "HUMIDITY", nullable = false)
    private int humidity;

    @Builder
    public CurrentWeather(float temperature, Region region, String weather, float windSpeed, int humidity) {
        this.temperature = temperature;
        this.region = region;
        this.weather = weather;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }

    public CurrentWeather update(float temperature, String weather){
        this.temperature = temperature;
        this.weather = weather;
        return this;
    }
}