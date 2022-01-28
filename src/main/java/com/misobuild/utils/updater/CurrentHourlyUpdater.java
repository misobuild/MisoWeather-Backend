package com.misobuild.utils.updater;

import com.misobuild.domain.weather.CurrentWeatherRepository;
import com.misobuild.domain.weather.HourlyForecastRepository;
import com.misobuild.domain.region.Region;
import com.misobuild.utils.reader.openweather.OpenWeatherReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentHourlyUpdater implements Updater{
    private final OpenWeatherReader openWeatherReader;
    private final CurrentWeatherRepository currentWeatherRepository;
    private final HourlyForecastRepository hourlyForecastRepository;

    @Override
    public void update(Region region) {
        openWeatherReader.configure(region);
        currentWeatherRepository.save(openWeatherReader.getCurrentWeather());
        hourlyForecastRepository.saveAll(openWeatherReader.getHourlyForecast());
    }
}
