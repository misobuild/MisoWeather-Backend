package com.misobuild.utils.reader.airkorea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawAirDust {
    private int pm10Value;
    private int pm25Value;
    private String dataTime;
    private String pm10Grade;
    private String pm25Grade;
    private String sidoName;
}
