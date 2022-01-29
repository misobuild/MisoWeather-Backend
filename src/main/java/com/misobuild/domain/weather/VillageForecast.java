package com.misobuild.domain.weather;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "FORECAST_VILLAGE_TB")
public class VillageForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FORECAST_VILLAGE_ID")
    private Long seq;

    @Column(name = "BASE_DATE", nullable = false, columnDefinition = "varchar(45)")
    private String baseDate;

    @Column(name = "BASE_TIME", nullable = false, columnDefinition = "varchar(45)")
    private String baseTime;

    @Column(name = "CATEGORY", nullable = false, columnDefinition = "varchar(45)")
    private String category;

    @Column(name = "FORECAST_DATE", nullable = false, columnDefinition = "varchar(45)")
    private String forecastDate;

    @Column(name = "FORECAST_TIME", nullable = false, columnDefinition = "varchar(45)")
    private String forecastTime;

    @Column(name = "FORECAST_VALUE", nullable = false, columnDefinition = "varchar(45)")
    private String forecastValue;

    @Column(name = "FORECAST_LOCATION_X", nullable = false, columnDefinition = "integer")
    private int forecastLocationX;

    @Column(name = "FORECAST_LOCATION_Y", nullable = false, columnDefinition = "integer")
    private int forecastLocationY;

    @Builder
    public VillageForecast(int forecastLocationX, int forecastLocationY, String baseDate, String baseTime, String category,
                           String forecastDate, String forecastTime, String forecastValue) {
        this.forecastLocationX = forecastLocationX;
        this.forecastLocationY = forecastLocationY;
        this.baseDate = baseDate;
        this.baseTime = baseTime;
        this.category = category;
        this.forecastDate = forecastDate;
        this.forecastTime = forecastTime;
        this.forecastValue = forecastValue;
    }
}