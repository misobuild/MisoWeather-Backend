package com.misobuild.utils.builder.oauth;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class KakaoAuthCallBuilder extends RestTemplateBuilder {
    String kakaoTokenCheckURL = "https://kapi.kakao.com/v1/user/access_token_info";
    String contentTypeValue = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public void addHeader() {
        headers.add("Authorization", "Bearer " + bearerToken);
        headers.add("Content-type", contentType);
    }

    public KakaoAuthCallBuilder(String socialToken) {
        headers = new HttpHeaders();
        restTemplate = new RestTemplate();
        url = kakaoTokenCheckURL;
        bearerToken = socialToken;
        contentType = contentTypeValue;
    }
}
