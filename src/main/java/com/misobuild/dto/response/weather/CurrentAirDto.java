package com.misobuild.dto.response.weather;

import com.misobuild.constants.AirEnum;
import com.misobuild.domain.weather.CurrentAir;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CurrentAirDto {
    private int fineDust;
    private String fineDustIcon;
    private int ultraFineDust;
    private String ultraFineDustIcon;
    private String fineDustGrade;
    private String ultraFineDustGrade;
    private String bigScale;

    @Builder
    public CurrentAirDto(int pm10, int pm25, String pm10Grade, String pm25Grade, String bigScale) {
        this.fineDust = pm10;
        this.ultraFineDust = pm25;
        this.fineDustGrade = AirEnum.getEnumForFineDust(pm10).getGrade();
        this.ultraFineDustGrade = AirEnum.getEnumForUltraDust(pm25).getGrade();
        this.bigScale = bigScale;
        this.fineDustIcon = AirEnum.getEnumForFineDust(pm10).getIcon();
        this.ultraFineDustIcon = AirEnum.getEnumForUltraDust(pm25).getIcon();
    }

    public static CurrentAirDto fromEntity(CurrentAir currentAir){
        return CurrentAirDto.builder()
                .pm10(currentAir.getPm10())
                .pm25(currentAir.getPm25())
                .pm10Grade(currentAir.getPm10Grade())
                .pm25Grade(currentAir.getPm25Grade())
                .bigScale(currentAir.getBigScale())
                .build();
    }
}
