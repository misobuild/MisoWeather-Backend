package com.misobuild.constants;

import lombok.Getter;

@Getter
public enum SkyEnum {
    CLEAR("1"),
    MOSTLYCLOUDY("3"),
    CLOUDY("4");

    private final String value;

    SkyEnum(String value) {
        this.value = value;
    }

    public static SkyEnum getEnum(String value) {
        for(SkyEnum v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
