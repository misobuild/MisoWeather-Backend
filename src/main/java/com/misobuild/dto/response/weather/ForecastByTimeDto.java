package com.misobuild.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ForecastByTimeDto {
    @JsonIgnore
    private LocalDateTime dateTime;

    @ApiModelProperty(example = "15시")
    private String hour;

    @ApiModelProperty(example = "날씨 이모티콘")
    private String sky;

    @ApiModelProperty(example = "기온")
    private String temperature;

    public void setSky(String sky) {
        this.sky = sky;
    }

    @Builder
    public ForecastByTimeDto(String hour, String temperature, LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.hour = hour;
        this.temperature = temperature;
    }
}