package com.misobuild.utils.factory;

import com.misobuild.constants.WeatherEnum;

public class WeatherEnumFactory {
    public static WeatherEnum of(Long weatherId) {
        switch (weatherId.toString().substring(0, 1)) {
            case "2":
                return WeatherEnum.SHOWER;
            case "3":
            case "5":
                return WeatherEnum.RAIN;
            case "6":
                return WeatherEnum.SNOW;
            case "7":
                return WeatherEnum.MOSTLYCLOUDY;
            case "8":
                if(weatherId.equals(800L)) return WeatherEnum.CLEAR;
                if(weatherId.equals(801L) || weatherId.equals(802L)) return WeatherEnum.CLOUDY;
                return WeatherEnum.MOSTLYCLOUDY;
            default:
                return WeatherEnum.CLEAR;
        }
    }
}
