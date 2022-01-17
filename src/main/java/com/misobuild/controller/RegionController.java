package com.misobuild.controller;

import com.misobuild.api.ApiResponseWithData;
import com.misobuild.api.HttpStatusEnum;
import com.misobuild.dto.response.region.RegionResponseDto;
import com.misobuild.service.RegionService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"지역"})
@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @ApiOperation(value = "2단계 지역 리스트 요청", notes = "1단계에서 선택한 bigScaleRegion에 따른 2단계 지역리스트를 중복 없이 id 순서대로 보여준다")
    @ApiImplicitParam(name = "bigScaleRegion", value = "큰 단위 지역 구분 (유의: 세종특별자치시, 제주도)", example = "세종특별자치시")
    @GetMapping("/api/region/{bigScaleRegion}")
    public ResponseEntity<ApiResponseWithData<RegionResponseDto>> getMidScaleList(@PathVariable(name = "bigScaleRegion") String bigScaleRegion) {
        return ResponseEntity.ok(ApiResponseWithData.<RegionResponseDto>builder()
                .httpStatus(HttpStatusEnum.OK)
                .data(regionService.getMidScaleList(bigScaleRegion))
                .message("생성 성공")
                .build());
    }

    @ApiOperation(value = "3단계 지역 리스트 요청", notes = "2단계에서 선택한 midScaleRegion에 따른 3단계 지역리스트를 id 순서대로 보여준다")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bigScaleRegion", value = "가장 큰 단위의 지역 구분 (유의: 세종특별자치시, 제주도)", example = "경기도"),
            @ApiImplicitParam(name = "midScaleRegion", value= "중간 단위 지역 구분 midScaleRegion (특이사례: 고양시덕양구)", example = "고양시덕양구")
    })
    @GetMapping("/api/region/{bigScaleRegion}/{midScaleRegion}")
    public ResponseEntity<ApiResponseWithData<RegionResponseDto>> getSmallScaleList(@PathVariable(name = "bigScaleRegion") String bigScaleRegion, @PathVariable(name = "midScaleRegion") String midScaleRegion) {
        return ResponseEntity.ok(ApiResponseWithData.<RegionResponseDto>builder()
                .httpStatus(HttpStatusEnum.OK)
                .message("bigScale과 midScale로 찾아온 smallScale 리스트")
                .data(regionService.getSmallScaleList(bigScaleRegion, midScaleRegion))
                .build());
    }
}
