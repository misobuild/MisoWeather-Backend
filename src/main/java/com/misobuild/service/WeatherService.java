package com.misobuild.service;

import com.misobuild.constants.BigScaleEnum;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.forecast.CurrentAir;
import com.misobuild.domain.forecast.CurrentAirRedisRepository;
import com.misobuild.domain.forecast.VillageForecast;
import com.misobuild.domain.forecast.VillageForecastRepository;
import com.misobuild.domain.region.Region;
import com.misobuild.domain.region.RegionRepository;
import com.misobuild.dto.response.weather.*;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.caller.ForecastCaller;
import com.misobuild.utils.factory.UpdaterFactory;
import com.misobuild.utils.reader.ForecastReader;
import com.misobuild.utils.reader.WeatherReader;
import com.misobuild.utils.time.VillageForecastTime;
import com.misobuild.utils.updater.Updater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final VillageForecastRepository villageForecastRepository;
    private final CurrentAirRedisRepository currentAirRedisRepository;
    private final RegionRepository regionRepository;
    private final ForecastCaller forecastCaller;
    private final UpdaterFactory updaterFactory;
    private final WeatherReader weatherReader;

    public ForecastNowDto getForecastNow(Long regionId) throws IOException {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        VillageForecastTime villageForecastTime = new VillageForecastTime();
        int locationX = region.getLOCATION_X();
        int locationY = region.getLOCATION_Y();

        List<VillageForecast> rawVillageForecastList = villageForecastRepository
                .findByForecastLocationXAndForecastLocationY(locationX, locationY);

        // TODO 시간에 대한 책임 분리
        List<VillageForecast> candidateList = rawVillageForecastList.stream()
                .filter(item -> item.getBaseDate()
                        .equals(villageForecastTime.getBaseDateYear()
                                + villageForecastTime.getBaseDateMonth()
                                + villageForecastTime.getBaseDateDay()))
                .collect(Collectors.toList());

        if (candidateList.isEmpty()) {
            candidateList.addAll(forecastCaller.getVillageForecastList(locationX, locationY));
        }

        ForecastReader forecastReader = ForecastReader.builder()
                .villageForecastList(candidateList).build();

        return ForecastNowDto.builder()
                .region(region)
                .forecastByTimeDto(forecastReader.getForecastNow().get(0))
                .forecastNowInfo(forecastReader.getForecastNowInfo())
                .build();
    }

    public ForecastDto getForecastDetail(Long regionId) throws IOException {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new ApiCustomException(HttpStatusEnum.NOT_FOUND));

        VillageForecastTime villageForecastTime = new VillageForecastTime();
        int locationX = region.getLOCATION_X();
        int locationY = region.getLOCATION_Y();

        List<VillageForecast> rawVillageForecastList = villageForecastRepository
                .findByForecastLocationXAndForecastLocationY(locationX, locationY);

        // TODO 시간에 대한 책임 분리
        List<VillageForecast> candidateList = rawVillageForecastList.stream()
                .filter(item -> item.getBaseDate()
                        .equals(villageForecastTime.getBaseDateYear()
                                + villageForecastTime.getBaseDateMonth()
                                + villageForecastTime.getBaseDateDay()))
                .collect(Collectors.toList());

        if (candidateList.isEmpty()) {
            candidateList.addAll(forecastCaller.getVillageForecastList(locationX, locationY));
        }

        ForecastReader forecastReader = ForecastReader.builder()
                .villageForecastList(candidateList).build();

        return ForecastDto.builder()
                .region(region)
                .forecastByTimeDtoList(forecastReader.getForecastByTimeList())
                .forecastInfo(forecastReader.getForecastInfo())
                .build();
    }
}