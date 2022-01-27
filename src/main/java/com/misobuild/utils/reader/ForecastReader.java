package com.misobuild.utils.reader;

import com.misobuild.constants.SkyEnum;
import com.misobuild.constants.WeatherEnum;
import com.misobuild.domain.forecast.VillageForecast;
import com.misobuild.dto.response.weather.ForecastByTimeDto;
import com.misobuild.dto.response.weather.ForecastInfo;
import com.misobuild.dto.response.weather.ForecastNowInfo;
import com.misobuild.utils.time.CurrentForecastTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// TODO 게터 사용했으므로 함수 이름정리해야 한다.
@Getter
@RequiredArgsConstructor
public class ForecastReader {
    String codeSky = "SKY";
    String codeTemperature = "TMP";
    String codeRainSnow = "TCY";
    String rainSnow;
    String windSpeed;
    String humidity;

    List<VillageForecast> villageForecastList;
    List<ForecastByTimeDto> forecastByTimeDtoList;
    ForecastInfo forecastInfo;
    ForecastNowInfo forecastNowInfo;
    Map<String, String> currentTimeInfoMap = new HashMap<>();
    Map<String, String> currentDateTemperatureMap = new HashMap<>();

    public List<ForecastByTimeDto> getForecastByTimeList() {
        getForecastByTimeUsingTmp();
        setSky(getForecastByTimeUsingSKY());
        setRainSnow(getForecastByTimeUsingPTY());
        buildForecastInfo();
        return subListByTime();
    }

    public List<ForecastByTimeDto> getForecastNow() {
        getForecastByTimeUsingTmp();
        setSky(getForecastByTimeUsingSKY());
        setRainSnow(getForecastByTimeUsingPTY());
        buildForecastNowInfo();
        return subListByTime();
    }

    // TODO Enum 클래스 이용한 리팩토링 가능성
    public void getForecastByTimeUsingTmp() {
        forecastByTimeDtoList = villageForecastList.stream()
                .filter(villageForecast -> villageForecast.getCategory().equals(codeTemperature))
                .map(item -> ForecastByTimeDto.builder()
                        .hour(item.getForecastTime().substring(0, 2)).temperature(item.getForecastValue()).build())
                .collect(Collectors.toList());
    }

    public List<String> getForecastByTimeUsingSKY() {
        return villageForecastList.stream()
                .filter(villageForecast -> villageForecast.getCategory().equals(codeSky))
                .map(item -> WeatherEnum.valueOf(SkyEnum.getEnum(item.getForecastValue()).toString()))
                .map(WeatherEnum::getWeather)
                .collect(Collectors.toList());
    }

    public List<String> getForecastByTimeUsingPTY() {
        return villageForecastList.stream()
                .filter(villageForecast -> villageForecast.getCategory().equals(codeRainSnow))
                .map(item -> WeatherEnum.valueOf(SkyEnum.getEnum(item.getForecastValue()).toString()))
                .map(WeatherEnum::getWeather)
                .collect(Collectors.toList());
    }

    public void setSky(List<String> skyList) {
        for (int forecast = 0; forecast < skyList.size(); forecast++) {
            forecastByTimeDtoList.get(forecast).setSky(skyList.get(forecast));
        }
    }

    public void setRainSnow(List<String> rainSnowList) {
        for (int forecast = 0; forecast < rainSnowList.size(); forecast++) {
            if (!rainSnowList.get(forecast).equals("0"))
                forecastByTimeDtoList.get(forecast).setSky(rainSnowList.get(forecast));
        }
    }

    public List<ForecastByTimeDto> subListByTime() {
        int index = LocalDateTime.now().getHour();
        return forecastByTimeDtoList.subList(index, index + 12);
    }

    public void buildForecastInfo() {
        CurrentForecastTime currentForecastTime = new CurrentForecastTime();

        String forecastTime = currentForecastTime.getBaseTime();
        String forecastDate = currentForecastTime.getBaseDateYear()
                + currentForecastTime.getBaseDateMonth()
                + currentForecastTime.getBaseDateDay();

        List<VillageForecast> temperatureList = villageForecastList.stream()
                .filter(villageForecast -> villageForecast.getForecastDate().equals(forecastDate))
                .filter(villageForecast -> villageForecast.getCategory().equals("TMX")
                        || villageForecast.getCategory().equals("TMN"))
                .collect(Collectors.toList());

        List<VillageForecast> infoList = villageForecastList.stream()
                .filter(villageForecast -> villageForecast.getForecastDate().equals(forecastDate))
                .filter(villageForecast -> villageForecast.getForecastTime().equals(forecastTime))
                .collect(Collectors.toList());

        for (VillageForecast villageForecast : infoList){
            currentTimeInfoMap.put(villageForecast.getCategory().toLowerCase(Locale.ROOT)
                    , villageForecast.getForecastValue());
        }

        for (VillageForecast villageForecast : temperatureList){
            currentDateTemperatureMap.put(villageForecast.getCategory().toLowerCase(Locale.ROOT)
                    , villageForecast.getForecastValue());
        }

        forecastInfo = new ForecastInfo(currentTimeInfoMap, currentDateTemperatureMap);
    }

    public void buildForecastNowInfo() {
        CurrentForecastTime currentForecastTime = new CurrentForecastTime();

        String forecastTime = currentForecastTime.getBaseTime();
        String forecastDate = currentForecastTime.getBaseDateYear()
                + currentForecastTime.getBaseDateMonth()
                + currentForecastTime.getBaseDateDay();

        List<VillageForecast> temperatureList = villageForecastList.stream()
                .filter(villageForecast -> villageForecast.getForecastDate().equals(forecastDate))
                .filter(villageForecast -> villageForecast.getCategory().equals("TMX")
                        || villageForecast.getCategory().equals("TMN"))
                .collect(Collectors.toList());

        for (VillageForecast villageForecast : temperatureList){
            currentDateTemperatureMap.put(villageForecast.getCategory().toLowerCase(Locale.ROOT)
                    , villageForecast.getForecastValue());
        }

        forecastNowInfo = new ForecastNowInfo(currentDateTemperatureMap);
    }

    @Builder
    public ForecastReader(List<VillageForecast> villageForecastList) {
        this.villageForecastList = villageForecastList;
        this.forecastByTimeDtoList = new ArrayList<>();
    }
}
