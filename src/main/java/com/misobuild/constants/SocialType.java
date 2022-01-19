package com.misobuild.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialType {
    KAKAO("kakao"),
    APPLE("apple");

    private final String serviceName;

    public static SocialType getEnum(String value) {
        for(SocialType v : values())
            if(v.getServiceName().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
