package com.misobuild.utils.builder.weather;

import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class OpenWeatherCallBuilder extends RestTemplateBuilder {
    String openWeatherUrl = "https://api.openweathermap.org/data/2.5/onecall";
    String contentTypeValue = "application/x-www-form-urlencoded;charset=utf-8";
    String units = "metric";
    String exclude = "minutely";
    String latitude;
    String longitude;
    String serviceKey;


    @Override
    public void addHeader() {
        headers.add("Content-type", contentType);
    }

    @Builder
    public OpenWeatherCallBuilder(String serviceKey, String latitude, String longitude) {
        headers = new HttpHeaders();
        restTemplate = new RestTemplate();
        this.serviceKey = serviceKey;
        this.latitude = latitude;
        this.longitude = longitude;
        contentType = contentTypeValue;
        url = composeUrl();
    }

    public String composeUrl(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.openWeatherUrl).append("?")
                .append("appid=").append(serviceKey)
                .append("&lat=").append(latitude)
                .append("&lon=").append(longitude)
                .append("&exclude=").append(exclude)
                .append("&units=").append(this.units);
        return stringBuilder.toString();
    }
}
