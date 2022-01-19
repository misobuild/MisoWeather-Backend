package com.misobuild.constants;

import lombok.Getter;

@Getter
public enum RainSnowEnum {
    RAIN("1"),
    RAINORSNOW("2"),
    SNOW("3"),
    SHOWER("4");

    private final String value;

    RainSnowEnum(String value) {
        this.value = value;
    }

    public static RainSnowEnum getEnum(String value) {
        for(RainSnowEnum v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
