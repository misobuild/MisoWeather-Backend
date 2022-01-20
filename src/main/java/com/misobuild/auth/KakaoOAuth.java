package com.misobuild.auth;

import com.misobuild.exception.ApiCustomException;
import com.misobuild.constants.HttpStatusEnum;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoOAuth {
    String kakaoTokenCheckURL = "https://kapi.kakao.com/v1/user/access_token_info";
    String contentTypeValue = "application/x-www-form-urlencoded;charset=utf-8";

    public Long getUserId(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", contentTypeValue);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);
        JSONObject body;

        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    kakaoTokenCheckURL,
                    HttpMethod.GET,
                    kakaoProfileRequest,
                    String.class
            );

            body = new JSONObject(response.getBody());
        }
        catch (HttpClientErrorException e){
            throw new ApiCustomException(HttpStatusEnum.valueOf(HttpStatus.valueOf(e.getRawStatusCode()).name()));
        }

        return body.getLong("id");
    }
}