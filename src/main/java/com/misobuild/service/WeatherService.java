package com.misobuild.service;

import com.misobuild.constants.BigScaleEnum;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.weather.CurrentAir;
import com.misobuild.domain.weather.CurrentAirRedisRepository;
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
import java.net.URISyntaxException;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final RegionRepository regionRepository;
    private final CurrentAirRedisRepository currentAirRedisRepository;
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

    public CurrentAirDto getAirDust(Long regionId) throws URISyntaxException {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));
        Long key = BigScaleEnum.getEnum(region.getBigScale()).getRedisKey();
        CurrentAir currentAir = currentAirRedisRepository.findById(BigScaleEnum.getEnum(region.getBigScale()).getRedisKey())
                .orElse(null);
        if(currentAir == null) return CurrentAirDto.fromEntity(callAirDust(region));

        return CurrentAirDto.fromEntity(currentAir);
    }

    public CurrentAir callAirDust(Region region) throws URISyntaxException {
        CurrentAir currentAir = weatherReader.getAirDust(region);
        currentAirRedisRepository.save(currentAir);
        return currentAir;
    }
}