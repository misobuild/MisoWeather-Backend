package com.misobuild.dto.response.region;

import com.misobuild.domain.region.Region;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class RegionResponseDto {
    private List<Region> regionList;

    @Builder
    public RegionResponseDto(List<Region> midScaleList){
        this.regionList = midScaleList;
    }
}