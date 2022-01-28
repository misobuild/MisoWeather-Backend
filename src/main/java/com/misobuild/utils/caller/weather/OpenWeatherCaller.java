package com.misobuild.utils.caller.weather;

import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.region.Region;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.builder.weather.OpenWeatherCallBuilder;
import com.misobuild.utils.caller.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class OpenWeatherCaller implements ApiCaller {
    @Value("${app.auth.openWeatherServiceKey}")
    public String serviceKey;
    public String latitude;
    public String longitude;

    @PostConstruct
    public void init() {
    }

    public JSONObject call() {
        OpenWeatherCallBuilder openWeatherCallBuilder = OpenWeatherCallBuilder.builder()
                .serviceKey(serviceKey)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        try {
            ResponseEntity<String> response = openWeatherCallBuilder.restTemplate.exchange(
                    openWeatherCallBuilder.url,
                    HttpMethod.GET,
                    openWeatherCallBuilder.httpEntityHeader,
                    String.class);
            return new JSONObject(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new ApiCustomException(HttpStatusEnum
                    .valueOf(HttpStatus.valueOf(e.getRawStatusCode()).name()));
        }
    }

    public void setLatitudeAndLongitude(Region region){
        this.latitude = Float.toString(region.getLatitude());
        this.longitude = Float.toString(region.getLongitude());
    }
}
