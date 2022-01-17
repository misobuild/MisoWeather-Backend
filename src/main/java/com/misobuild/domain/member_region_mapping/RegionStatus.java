package com.misobuild.domain.member_region_mapping;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RegionStatus {

    DEFAULT("DEFAULT","기본 지역"),
    NORMAL("ADDED","추가 지역");

    private final String status;
    private final String description;
}
