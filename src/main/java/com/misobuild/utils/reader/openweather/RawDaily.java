package com.misobuild.utils.reader.openweather;

import lombok.Getter;

import java.util.List;

// TODO @Getter는 멤벼변수 오버라이드 햇을 경우에 오류가 생긴다.
@Getter
public class RawDaily{
    private TempInfo temp;
    private FeelsLike feels_like;
    private int moonrise;
    private int moonset;
    private float moon_phase;
    private float pop;
    private int rain;
    private int snow;
    private int dt;
    private int sunrise;
    private int senset;
    private int pressure;
    private int humidity;
    private float dew_point;
    private int uvi;
    private int clouds;
    private int visibility;
    private float wind_speed;
    private int wind_deg;
    private int wind_gust;
    private List<RawCurrent.Weather> weather;

    @Getter
    public static class Weather {
        private Long id;
        private String main;
        private String description;
        private String icon;
    }

    @Getter
    public static class FeelsLike{
        private float day;
        private float night;
        private float eve;
        private float morn;
    }

    @Getter
    public static class TempInfo{
        private float day;
        private float min;
        private float max;
        private float night;
        private float eve;
        private float morn;
    }
}
