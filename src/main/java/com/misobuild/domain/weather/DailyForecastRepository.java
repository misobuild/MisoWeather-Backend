package com.misobuild.domain.weather;

import com.misobuild.domain.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyForecastRepository extends JpaRepository<DailyForecast, Long> {
    Optional<DailyForecast> findFirstByForecastTimeIsAfterAndRegion(LocalDateTime localDateTime, Region region);
    List<DailyForecast> findByForecastTimeAfterAndRegionOrderByForecastTimeAsc(LocalDateTime localDateTime, Region region);
}