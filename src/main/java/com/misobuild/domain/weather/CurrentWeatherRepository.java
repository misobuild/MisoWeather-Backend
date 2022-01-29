package com.misobuild.domain.weather;

import com.misobuild.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CurrentWeatherRepository extends JpaRepository<CurrentWeather, Long> {
    Optional<CurrentWeather> findByRegion(Region region);
    Optional<CurrentWeather> findFirstByCreatedAtIsAfterAndRegion(LocalDateTime startTime, Region region);
    Optional<LocalDateTime> findLocalDateTimeByRegion(Region region);

//    @Async
//    public <S extends CurrentWeather> S save(S entity);
}