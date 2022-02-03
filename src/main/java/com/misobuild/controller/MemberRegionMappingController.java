package com.misobuild.controller;

import com.misobuild.api.ApiResponseWithData;
import com.misobuild.auth.UserDetailsImpl;
import com.misobuild.constants.HttpStatusEnum;
import com.misobuild.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"사용자-지역"})
@RestController
@RequiredArgsConstructor
public class MemberRegionMappingController {
    private final RegionService regionService;

    @ApiOperation(value = "사용자 지역 정보 변경", notes = "사용자의 기본 지역을 바꿉니다")
    @PutMapping("/api/member-region-mapping/default")
    @ApiImplicitParam(name = "regionId", value = "지역 아이디", example = "15")
    public ResponseEntity<ApiResponseWithData<Long>> checkVersion(@AuthenticationPrincipal UserDetailsImpl userDetails, Long regionId) {
        return ResponseEntity.ok(ApiResponseWithData.<Long>builder()
                .status(HttpStatusEnum.OK)
                .data(regionService.updateRegion(userDetails.getMember(), regionId).getRegion().getId())
                .message("Update Successful")
                .build());
    }
}
