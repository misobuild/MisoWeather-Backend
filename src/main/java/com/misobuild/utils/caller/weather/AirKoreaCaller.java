package com.misobuild.utils.caller.weather;

import com.misobuild.constants.BigScaleEnum;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.builder.weather.AirKoreaBuilder;
import com.misobuild.utils.caller.ApiCaller;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AirKoreaCaller implements ApiCaller {
    @Value("${app.auth.serviceKey}")
    public String serviceKey;
    public String bigScale;

    @PostConstruct
    public void init() {
    }

    public JSONObject call() {
        AirKoreaBuilder airKoreaBuilder = AirKoreaBuilder.builder()
                .serviceKey(serviceKey)
                .bigScale(BigScaleEnum.getEnum(bigScale).toString())
                .build();

        airKoreaBuilder.addHeader();
        airKoreaBuilder.setHttpEntityHeader();

        try{
            ResponseEntity<String> response = airKoreaBuilder.restTemplate.getForEntity(airKoreaBuilder.url,
                    String.class);
            return new JSONObject(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new ApiCustomException(HttpStatusEnum
                    .valueOf(HttpStatus.valueOf(e.getRawStatusCode()).name()));
        }
    }

    public void setBigScale(String bigScale){
        this.bigScale = bigScale;
    }
}
