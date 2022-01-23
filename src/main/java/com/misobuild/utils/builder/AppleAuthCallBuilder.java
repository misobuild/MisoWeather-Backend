package com.misobuild.utils.builder;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

/**
 * identityToken의 RSA 검증을 위해 요청을 보낼 객체를 만듭니다.
 *
 * @author yeon
 **/
public class AppleAuthCallBuilder extends RestTemplateBuilder {
    String appleAuthCheckURL = "https://appleid.apple.com/auth/keys";
    String contentTypeValue = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public void addHeader() {
        headers.add("Content-type", contentType);
    }

    public AppleAuthCallBuilder() {
        headers = new HttpHeaders();
        restTemplate = new RestTemplate();
        url = appleAuthCheckURL;
        contentType = contentTypeValue;
    }
}
