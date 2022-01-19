package com.misobuild.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO 생성자 정의할 거면 하고 안할 거면 어노테이션 쓰고 하나만 하자
@Getter
@AllArgsConstructor
public enum BigScaleEnum {
    서울("서울특별시", 0L, "서울"),
    경기("경기도", 1L, "경기"),
    인천("인천광역시", 2L, "인천"),
    대전("대전광역시", 3L, "대전"),
    세종("세종특별자치시", 4L, "세종"),
    충북("충청북도", 5L, "충북"),
    충남("충청남도", 6L, "충남"),
    광주("광주광역시", 7L, "광주"),
    전북("전라북도", 8L,"전북"),
    전남("전라남도", 9L, "전남"),
    대구("대구광역시", 10L, "대구"),
    부산("부산광역시", 11L, "부산"),
    울산("울산광역시", 12L, "을신"),
    경북("경상북도", 13L, "경북"),
    경남("경상남도", 14L, "경남"),
    강원("강원도", 15L, "강원"),
    제주("제주도",16L, "제주");

    private final String value;
    private final Long redisKey;
    private final String shortValue;


    public static BigScaleEnum getEnum(String value) {
        for(BigScaleEnum v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

    public static BigScaleEnum getEnumByShortValues(String shortValue) {
        for(BigScaleEnum v : values())
            if(v.getShortValue().equalsIgnoreCase(shortValue)) return v;
        throw new IllegalArgumentException();
    }
}
