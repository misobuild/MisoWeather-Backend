package com.misobuild.utils.builder.weather;

import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class AirKoreaBuilder extends NewRestTemplateBuilder {
    public String airKoreaUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
    String contentTypeValue = "application/x-www-form-urlencoded;charset=utf-8";
    String decodedKey = "Yz6FMU6yCj6mLJFIxk1oAyuj+mVU4eOGVPhIoc/8gWC4t/DEB7fsV7F3DjKaMCA5l957daC7RDxaGIyj5LS1iQ==";
    String returnType = "json";
    String numOfRows = "1";
    String pageNo = "1";
    String version = "1.3";
    String serviceKey;
    String sidoName;


    @Override
    public void addHeader() {
        headers.add("Content-type", contentType);
    }

    @Builder
    public AirKoreaBuilder(String serviceKey, String bigScale) {
        headers = new HttpHeaders();
        restTemplate = new RestTemplate();
        this.serviceKey = serviceKey;
        this.sidoName = bigScale;
        contentType = contentTypeValue;
        url = composeUrl();
    }

    public URI composeUrl() {
        try {
            return new URI(String.format("%s?returnType=%s&numOfRows=%s&pageNo=%s&sidoName=%s&ver=%s&serviceKey=%s",
                    airKoreaUrl,
                    URLEncoder.encode(returnType,"UTF-8"),
                    URLEncoder.encode(numOfRows,"UTF-8"),
                    URLEncoder.encode(pageNo,"UTF-8"),
                    URLEncoder.encode(sidoName,"UTF-8"),
                    URLEncoder.encode(version,"UTF-8"),
                    serviceKey));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return UriComponentsBuilder.fromHttpUrl(airKoreaUrl)
//                .queryParam("returnType", returnType)
//                .queryParam("numOfRows", numOfRows)
//                .queryParam("pageNo", pageNo)
//                .queryParam("sidoName", sidoName)
//                .queryParam("ver", version)
//                .queryParam("serviceKey", serviceKey)
//                .build(false)
//                .toUri();
