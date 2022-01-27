package com.misobuild.domain.forecast;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillageForecastRepository extends JpaRepository<VillageForecast, String> {
    List<VillageForecast> findByForecastLocationXAndForecastLocationY(int forecastLocationX, int forecastLocationY);
    // List<VillageForecast> findByForecastLatitudeAndLongitude(float latitude, float longitude);
}