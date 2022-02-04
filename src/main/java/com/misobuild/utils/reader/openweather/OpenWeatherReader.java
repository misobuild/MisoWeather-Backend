package com.misobuild.utils.reader.openweather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.weather.CurrentWeather;
import com.misobuild.domain.weather.DailyForecast;
import com.misobuild.domain.weather.HourlyForecast;
import com.misobuild.domain.region.Region;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.caller.weather.OpenWeatherCaller;
import com.misobuild.utils.factory.WeatherEnumFactory;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OpenWeatherReader {
    private static final int FIRST_INDEX = 0;
    private final OpenWeatherCaller openWeatherCaller;
    private Region region;
    private ObjectMapper objectMapper;
    private JSONObject rawResponse;

    public CurrentWeather getCurrentWeather() {
        try {
            RawCurrent rawWeather = objectMapper.readValue(rawResponse.getJSONObject("current").toString(), RawCurrent.class);
            return CurrentWeather.builder()
                    .weather(WeatherEnumFactory.of(rawWeather.getWeather().get(FIRST_INDEX).getId()).getWeather())
                    .region(region)
                    .temperature(rawWeather.getTemp())
                    .humidity(rawWeather.getHumidity())
                    .windSpeed(rawWeather.getWind_speed())
                    .build();
        } catch (Exception e) {
            throw new ApiCustomException(HttpStatusEnum.INTERNAL_SERER_ERROR);
        }
    }

    public List<HourlyForecast> getHourlyForecast() {
        try {
            List<RawHourly> rawHourlyList = objectMapper
                    .readValue(rawResponse.getJSONArray("hourly").toString(), new TypeReference<List<RawHourly>>() {});
            return rawHourlyList.stream()
                    .map(rawHourly -> HourlyForecast.of(rawHourly, region, FIRST_INDEX))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApiCustomException(HttpStatusEnum.INTERNAL_SERER_ERROR);
        }
    }

    public List<DailyForecast> getDailyForecast() {
        try {
            List<RawDaily> rawDailyList = objectMapper
                    .readValue(rawResponse.getJSONArray("daily").toString(), new TypeReference<List<RawDaily>>() {
                    });
            return rawDailyList.stream()
                    .map(rawDaily -> DailyForecast.builder()
                            .weather(WeatherEnumFactory.of(rawDaily.getWeather().get(FIRST_INDEX).getId()).getWeather())
                            .forecastTime(Instant.ofEpochSecond(rawDaily.getDt()).atZone(ZoneId.systemDefault()).toLocalDateTime())
                            .maxTemperature(rawDaily.getTemp().getMax())
                            .minTemperature(rawDaily.getTemp().getMin())
                            .pop((int) rawDaily.getPop() * 100)
                            .rain(rawDaily.getRain())
                            .snow(rawDaily.getSnow())
                            .region(region)
                            .build()
                    )
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ApiCustomException(HttpStatusEnum.INTERNAL_SERER_ERROR);
        }
    }

    public void configure(Region region) {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        openWeatherCaller.setLatitudeAndLongitude(region);
        this.rawResponse = openWeatherCaller.call();
        this.region = region;
    }


}
