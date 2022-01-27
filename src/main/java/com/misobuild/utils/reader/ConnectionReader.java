package com.misobuild.utils.reader;

import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.exception.ApiCustomException;
import lombok.Builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionReader {
    String requestMethod;
    String requestPropertyKey;
    String requestPropertyValue;

    public String getApiConnection(String urlLink) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        URL url = new URL(urlLink);
        String line;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty(requestPropertyKey, requestPropertyKey);

        BufferedReader bufferedReader;
        if (conn.getResponseCode() < 200 && conn.getResponseCode() > 300) {
            throw new ApiCustomException(HttpStatusEnum.INTERNAL_SERER_ERROR);
        }

        bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        conn.disconnect();

        return stringBuilder.toString();
    }

    @Builder
    public ConnectionReader(String requestMethod, String requestPropertyKey, String requestPropertyValue) {
        this.requestMethod = requestMethod;
        this.requestPropertyKey = requestPropertyKey;
        this.requestPropertyValue = requestPropertyValue;
    }
}
