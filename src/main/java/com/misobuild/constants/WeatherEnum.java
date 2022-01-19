package com.misobuild.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum WeatherEnum {
    CLEAR("☀️"),
    MOSTLYCLOUDY("️⛅️"),
    CLOUDY("☁️"),
    RAIN("\uD83C\uDF27️"),
    RAINORSNOW("\uD83C\uDF27️"),
    SNOW("️️\uD83C\uDF28️"),
    FOLDEDUMBRELLA("\uD83C\uDF02"),
    UMBRELLA("☂️"),
    UMBRELLAWITHRAIN("☔️"),
    SHOWER("\uD83C\uDF27");

    private final String weather;
}
