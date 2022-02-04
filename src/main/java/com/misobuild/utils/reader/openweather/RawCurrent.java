package com.misobuild.utils.reader.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawCurrent {
    private int dt;
    private int sunrise;
    private int senset;
    private float temp;
    private float feels_like;
    private int pressure;
    private int humidity;
    private float dew_point;
    private int uvi;
    private int clouds;
    private int visibility;
    private float wind_speed;
    private int wind_deg;
    private int wind_gust;
    private List<Weather> weather;

    @Getter
    public static class Weather {
        private Long id;
        private String main;
        private String description;
        private String icon;
    }
}
