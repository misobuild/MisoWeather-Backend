package com.misobuild.utils.reader;

import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.weather.*;
import com.misobuild.domain.region.Region;
import com.misobuild.dto.response.weather.CurrentWeatherDto;
import com.misobuild.dto.response.weather.DailyForecastDto;
import com.misobuild.dto.response.weather.HourlyForecastDto;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.reader.airkorea.AirKoreaReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class WeatherReader {
    private final CurrentWeatherRepository currentWeatherRepository;
    private final DailyForecastRepository dailyForecastRepository;
    private final HourlyForecastRepository hourlyForecastRepository;
    private final AirKoreaReader airKoreaReader;
    private static Long ONE_HOUR = 1L;

    public CurrentWeatherDto getCurrentWeather(Region region){
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime dailyPivotTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime currentPivotTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(nowTime.getHour(), 0));

        CurrentWeather currentWeather = currentWeatherRepository.findFirstByCreatedAtIsAfterAndRegion(currentPivotTime, region)
                .orElseThrow( () -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));
        DailyForecast dailyForecast = dailyForecastRepository.findFirstByForecastTimeIsAfterAndRegion(dailyPivotTime, region)
                .orElseThrow( () -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return CurrentWeatherDto.fromEntity(currentWeather, dailyForecast);
    }

    // TODO x, y??? ???????????? ???????????? ?????????, 3773?????? ???????????? ?????? ????????? ?????? ?????????! ?????? ????????? ??????... ????????? ???????????? ??? ????????? ??????.
    // TODO update??? ????????? ???????????? ?????????, post??? ????????? ??????. ????????? ?????? ??? ??? ???????????? ?????????.
    public HourlyForecastDto getHourlyForecast(Region region){
        LocalDateTime pivotTime = LocalDateTime.now().minusMinutes(LocalTime.now().getMinute());
        // TODO ?????? ?????? ????????? ??? ????????? ?????? ???????????? ??? ?????????
        List<HourlyForecast> hourlyForecastList = hourlyForecastRepository.findFirst12ByForecastTimeIsAfterAndRegion(pivotTime, region)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return HourlyForecastDto.of(hourlyForecastList, region);
    }

    public DailyForecastDto getDailyForecast(Region region){
        LocalDateTime pivotTime = LocalDateTime.now().minusHours(LocalDateTime.now().getHour());
        List<DailyForecast> dailyForecastList = dailyForecastRepository.findByForecastTimeAfterAndRegionOrderByForecastTimeAsc(pivotTime, region);

        List<DailyForecast> resultList = dailyForecastList.stream()
                .filter(distinctByKey(DailyForecast::getForecastTime))
                .limit(7L)
                .collect(Collectors.toList());

        return DailyForecastDto.of(resultList, region);
    }

    public CurrentAir getAirDust(Region region) throws URISyntaxException {
        airKoreaReader.configure(region);
        return airKoreaReader.getAirDust();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
