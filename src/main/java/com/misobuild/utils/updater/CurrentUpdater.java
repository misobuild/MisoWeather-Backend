package com.misobuild.utils.updater;

import com.misobuild.domain.weather.CurrentWeatherRepository;
import com.misobuild.domain.region.Region;
import com.misobuild.utils.reader.openweather.OpenWeatherReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class CurrentUpdater implements Updater{
    private final OpenWeatherReader openWeatherReader;
    private final CurrentWeatherRepository currentWeatherRepository;

    @Override
    public void update(Region region) {
        currentWeatherRepository.findFirstByCreatedAtIsAfterAndRegion(LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), 0)), region)
                .ifPresentOrElse(currentWeather -> currentWeather.getSeq(), () -> {
                            openWeatherReader.configure(region);
                            currentWeatherRepository.save(openWeatherReader.getCurrentWeather());
                        }
                );
//        openWeatherReader.configure(region);
//        currentWeatherRepository.save(openWeatherReader.getCurrentWeather());
    }
}
