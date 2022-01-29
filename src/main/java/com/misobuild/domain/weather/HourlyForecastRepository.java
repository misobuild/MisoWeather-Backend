package com.misobuild.domain.weather;

import com.misobuild.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HourlyForecastRepository extends JpaRepository<HourlyForecast, Long> {
    Optional<List<HourlyForecast>> findFirst12ByForecastTimeIsAfterAndRegion(LocalDateTime localDateTime, Region region);
    Optional<List<HourlyForecast>> findFirstByForecastTimeIsAfterAndRegion(LocalDateTime localDateTime, Region region);


//    @Async
//    public <S extends HourlyForecast> List<S> saveAll(Iterable<S> entities);

}