package com.misobuild.domain.region;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region,Long> {
    List<Region> findByBigScale(String bigScale);
    List<Region> findByBigScaleAndMidScale(String bigScale, String midScale);
}
