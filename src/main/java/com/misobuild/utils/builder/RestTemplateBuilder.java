package com.misobuild.utils.builder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

abstract class RestTemplateBuilder {
    public String url;
    public String bearerToken;
    public String contentType;
    public HttpHeaders headers;
    public RestTemplate restTemplate;
    public HttpEntity<MultiValueMap<String, String>> httpEntityHeader;

    public void setHttpEntityHeader(){
        httpEntityHeader = new HttpEntity<>(headers);
    }

    abstract void addHeader();
}
