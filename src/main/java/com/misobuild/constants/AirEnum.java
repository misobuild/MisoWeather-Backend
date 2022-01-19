package com.misobuild.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO 생성자 정의할 거면 하고 안할 거면 어노테이션 쓰고 하나만 하자
@Getter
@AllArgsConstructor
public enum AirEnum {
    GOOD("\uD83D\uDE0A", 30, 15, "좋음"),
    NORMAL("\uD83D\uDE10", 80, 35, "보통"),
    BAD("☹️", 150, 75, "나쁨"),
    VERYVBAD("\uD83D\uDE21", 99999, 99999, "매우나쁨");

    private final String icon;
    private final int findDustCriteria;
    private final int ultraFineDustCriteria;
    private final String grade;


    public static AirEnum getEnumForFineDust(int value) {
        for(AirEnum v : values())
            if(value < v.getFindDustCriteria()) return v;
        throw new IllegalArgumentException();
    }

    public static AirEnum getEnumForUltraDust(int value) {
        for(AirEnum v : values())
            if(value < v.getUltraFineDustCriteria()) return v;
        throw new IllegalArgumentException();
    }
}
