package com.misobuild.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegionEnum {

    DEFAULT("DEFAULT","기본 지역"),
    NORMAL("ADDED","추가 지역");

    private final String status;
    private final String description;
}
