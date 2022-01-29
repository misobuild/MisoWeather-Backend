package com.misobuild.utils.builder.weather;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

abstract class NewRestTemplateBuilder {
    public URI url;
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
