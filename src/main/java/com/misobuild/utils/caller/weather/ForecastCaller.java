package com.misobuild.utils.caller.weather;

import com.misobuild.domain.weather.VillageForecast;
import com.misobuild.domain.weather.VillageForecastRepository;
import com.misobuild.utils.builder.VillageForecastApiUrlBuilder;
import com.misobuild.utils.time.VillageForecastTime;
import com.nimbusds.jose.shaded.json.writer.JsonReader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ForecastCaller {
    private final VillageForecastRepository villageForecastRepository;

    @Value("${app.auth.serviceKey}")
    public String serviceKey;

    @PostConstruct
    public void init() {
    }

    public List<VillageForecast> getVillageForecastList(int forecastLocationX, int forecastLocationY) throws IOException {
        VillageForecastApiUrlBuilder urlBuilder = VillageForecastApiUrlBuilder.builder()
                .villageForecastTime(new VillageForecastTime())
                .serviceKey(serviceKey)
                .locationX(forecastLocationX)
                .locationY(forecastLocationY)
                .build();

        ConnectionReader connectionReader = ConnectionReader.builder()
                .requestMethod("GET")
                .requestPropertyKey("Content-type")
                .requestPropertyValue("application/json")
                .build();

        JsonReader jsonReader = JsonReader.builder()
                .result(connectionReader.getApiConnection(urlBuilder.getUrlLink()))
                .locationX(forecastLocationX)
                .locationY(forecastLocationY)
                .build();

        return villageForecastRepository.saveAll(jsonReader.getVillageForecastList());
    }
}
