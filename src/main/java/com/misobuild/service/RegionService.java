package com.misobuild.service;

import com.misobuild.auth.JwtTokenProvider;
import com.misobuild.domain.region.Region;
import com.misobuild.domain.region.RegionReader;
import com.misobuild.domain.region.RegionRepository;
import com.misobuild.dto.response.region.RegionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RegionReader regionReader;

    public RegionResponseDto getMidScaleList(String bigScale) {
        List<Region> rawMidScaleList = regionRepository.findByBigScale(bigScale);

        return RegionResponseDto.builder()
                .midScaleList(regionReader.filterMidScaleList(rawMidScaleList))
                .build();
    }

    public RegionResponseDto getSmallScaleList(String bigScale, String midScale) {
        List<Region> rawSmallScaleList = regionRepository.findByBigScaleAndMidScale(bigScale, midScale);

        return RegionResponseDto.builder()
                .midScaleList(rawSmallScaleList)
                .build();
    }
}
