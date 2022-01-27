package com.misobuild.utils.reader;

import org.springframework.stereotype.Component;

@Component
public class ContentReader {
    public String checker(String content){
        return content.replaceAll("(\r\n|\r|\n|\n\r)", " ");
    }
}
