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

    // TODO x, y로 저장해서 찾아와야 하는데, 3773개의 데이터를 모두 필요로 하게 생겼다! 너무 중요한 이슈... 테이블 내용부터 다 바꿔야 한다.
    // TODO update는 리소스 추가이기 때문에, post로 바꿔야 한다. 이름이 다른 게 더 좋을지도 모른다.
    public HourlyForecastDto getHourlyForecast(Region region){
        LocalDateTime pivotTime = LocalDateTime.now().minusMinutes(LocalTime.now().getMinute());
        // TODO 이거 내가 알기로 빈 리스트 그냥 돌려주는 데 없으면
        List<HourlyForecast> hourlyForecastList = hourlyForecastRepository.findFirst12ByForecastTimeIsAfterAndRegion(pivotTime, region)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return HourlyForecastDto.of(hourlyForecastList, region);
    }

    public DailyForecastDto getDailyForecast(Region region){
        LocalDateTime pivotTime = LocalDateTime.now().minusHours(LocalDateTime.now().getHour());
        List<DailyForecast> dailyForecastList = dailyForecastRepository.findTop7ByForecastTimeAfterAndRegion(pivotTime, region);

        return DailyForecastDto.of(dailyForecastList, region);
    }

    public CurrentAir getAirDust(Region region) throws URISyntaxException {
        airKoreaReader.configure(region);
        return airKoreaReader.getAirDust();
    }
}
