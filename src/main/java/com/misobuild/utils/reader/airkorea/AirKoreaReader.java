package com.misobuild.utils.reader.airkorea;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.domain.weather.CurrentAir;
import com.misobuild.domain.region.Region;
import com.misobuild.exception.ApiCustomException;
import com.misobuild.utils.caller.weather.AirKoreaCaller;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class AirKoreaReader {
    private final AirKoreaCaller airKoreaCaller;
    ObjectMapper objectMapper;
    JSONObject rawResponse;
    Region region;

    public CurrentAir getAirDust() {
        try {
            RawAirDust rawAirDust = objectMapper
                    .readValue(rawResponse
                            .getJSONObject("response")
                            .getJSONObject("body")
                            .getJSONArray("items")
                            .get(0)
                            .toString(), RawAirDust.class);
            return CurrentAir.builder()
                    .bigScale(rawAirDust.getSidoName())
                    .pm10(rawAirDust.getPm10Value())
                    .pm25(rawAirDust.getPm25Value())
                    .pm10Grade(rawAirDust.getPm10Grade())
                    .pm25Grade(rawAirDust.getPm25Grade())
                    .build();
        } catch (Exception e) {
            throw new ApiCustomException(HttpStatusEnum.INTERNAL_SERER_ERROR);
        }
    }

    public void configure(Region region) throws URISyntaxException {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        airKoreaCaller.setBigScale(region.getBigScale());
        this.rawResponse = airKoreaCaller.call();
        this.region = region;
    }
}
