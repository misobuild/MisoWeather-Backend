package com.misobuild.dto.response.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Map;

@Getter
public class ForecastInfo {
    @JsonIgnore
    private String sky;

    @JsonIgnore
    private String pop;

    @JsonIgnore
    private String pty;

    @JsonIgnore
    private String reh;

    @JsonIgnore
    private String sno;

    @JsonIgnore
    private String tmp;

    @JsonIgnore
    private String wsd;

    @JsonIgnore
    private String pcp;
    private String temperatureMax;
    private String temperatureMin;
    private String rainSnow;
    private String rainSnowPossibility;
    private String rainSnowValue;
    private String windSpeed;
    private String windSpeedValue;
    private String humidity;
    private String humidityValue;

    public ForecastInfo(Map<String, String> infoMap, Map<String, String> temperatureMap) {
        this.sky = infoMap.get("sky");
        this.pop = infoMap.get("pop");
        this.pty = infoMap.get("pty");
        this.reh = infoMap.get("reh");
        this.sno = infoMap.get("sno");
        this.tmp = infoMap.get("tmp");
        this.wsd = infoMap.get("wsd");
        this.pcp = infoMap.get("pcp");
        this.temperatureMax = temperatureMap.get("tmx");
        this.temperatureMin = temperatureMap.get("tmn");

        setHumidityAndValue();
        setRainSnowAndValue();
        setWindSpeedAndValue();
    }

    public void setRainSnowAndValue() {
        if(!this.pty.equals("3")){
            if (Integer.parseInt(this.pop) < 40){
                this.rainSnow = "\uD83C\uDF02";
            }
            else if(Integer.parseInt(this.pop) < 60){
                this.rainSnow = "☂️";
            }
            else{
                this.rainSnow = "☔️";
            }
            this.rainSnowValue = this.pcp;
        }
        else{
            this.rainSnow = "☔️";
            this.rainSnowValue = this.sno;
        }
        this.rainSnowPossibility = this.pop;
    }

    public void setHumidityAndValue() {
        if(Integer.parseInt(this.reh) < 60) this.humidity = "💧";
        else this.humidity = "\uD83D\uDCA6";

        this.humidityValue = this.reh;
    }

    public void setWindSpeedAndValue() {
        if(Float.parseFloat(this.wsd) < 5) this.windSpeed = "\uD83C\uDF43";
        else if(Float.parseFloat(this.wsd) < 10) this.windSpeed = "\uD83C\uDF2C";
        else if(Float.parseFloat(this.wsd) < 17) this.windSpeed = "\uD83D\uDCA8";
        else this.windSpeed = "\uD83C\uDF2A";

        this.windSpeedValue = this.wsd;
    }
}
