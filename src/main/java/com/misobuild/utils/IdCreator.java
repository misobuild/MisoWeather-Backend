package com.misobuild.utils;

import org.springframework.stereotype.Component;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class IdCreator {
    public Long getRandomId(Long number){
        int randomNumber = ThreadLocalRandom
                .current()
                .nextInt(1, Long.valueOf(number).intValue() + 1);

        return Long.valueOf(randomNumber);
    }
}
