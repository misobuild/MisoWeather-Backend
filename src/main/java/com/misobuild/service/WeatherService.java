package com.misobuild.service;

import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.weather.CurrentAirRedisRepository;
import com.misobuild.domain.weather.VillageForecastRepository;
import com.misobuild.domain.region.Region;
import com.misobuild.domain.region.RegionRepository;
import com.misobuild.dto.response.weather.*;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.factory.UpdaterFactory;
import com.misobuild.utils.reader.WeatherReader;
import com.misobuild.utils.updater.Updater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final RegionRepository regionRepository;
    private final UpdaterFactory updaterFactory;
    private final WeatherReader weatherReader;

    @Transactional
    public void update(Long regionId){
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        Updater updater = updaterFactory.of(region);
        updater.update(region);
    }

    public CurrentWeatherDto getCurrentWeather(Long regionId){
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return weatherReader.getCurrentWeather(region);
    }

    public HourlyForecastDto getHourlyForecast(Long regionId){
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return weatherReader.getHourlyForecast(region);
    }

    public DailyForecastDto getDailyForecast(Long regionId){
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        return weatherReader.getDailyForecast(region);
    }
}