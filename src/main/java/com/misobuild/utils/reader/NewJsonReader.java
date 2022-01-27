package com.misobuild.utils.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misobuild.domain.forecast.VillageForecast;
import com.misobuild.dto.api.ApiVillageForecastDto;
import lombok.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewJsonReader {
    static final List<String> exceptionCategoryList = Arrays.asList("WAV", "UUU", "VVV", "VEC");
    String result;
    int locationX;
    int locationY;

    public List<VillageForecast> getVillageForecastList() throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(result);
        JSONArray response = objectToJsonArray(jsonObject);
        List<ApiVillageForecastDto> rawVillageForecastList = getRawVillageForecastList(response);
        return getVillageForecastList(rawVillageForecastList);
    }

    public JSONArray objectToJsonArray(JSONObject object) {
        return object
                .getJSONObject("response")
                .getJSONObject("body")
                .getJSONObject("items")
                .getJSONArray("item");
    }

    public List<ApiVillageForecastDto> getRawVillageForecastList(JSONArray jsonArray) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ApiVillageForecastDto> rawVillageForecastList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            ApiVillageForecastDto villageForecast = objectMapper.readValue(jsonArray.getJSONObject(i).toString(), ApiVillageForecastDto.class);
            rawVillageForecastList.add(villageForecast);
        }

        return rawVillageForecastList;
    }

    public List<VillageForecast> getVillageForecastList(List<ApiVillageForecastDto> rawVillageForecastList){
        return rawVillageForecastList.stream()
                .filter(item -> !exceptionCategoryList.contains(item.getCategory()))
                .map(item -> VillageForecast.builder()
                        .forecastLocationX(locationX)
                        .forecastLocationY(locationY)
                        .baseDate(item.getBaseDate())
                        .baseTime(item.getBaseTime())
                        .category(item.getCategory())
                        .forecastDate(item.getFcstDate())
                        .forecastTime(item.getFcstTime())
                        .forecastValue(item.getFcstValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Builder
    public NewJsonReader(String result, int locationX, int locationY) {
        this.result = result;
        this.locationX = locationX;
        this.locationY = locationY;
    }
}
