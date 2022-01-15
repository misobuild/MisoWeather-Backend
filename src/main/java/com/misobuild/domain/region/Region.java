package com.misobuild.domain.region;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "REGION_TB")
public class Region {
    @Id
    @Column(name = "REGION_ID")
    private Long id;

    @Column(name = "BIGSCALE", nullable = false, columnDefinition = "varcahr(45)")
    private String bigScale;

    @Column(name = "MIDSCALE", nullable = false, columnDefinition = "varcahr(45)")
    private String midScale;

    @Column(name = "SMALLSCALE", nullable = false, columnDefinition = "varcahr(45)")
    private String smallScale;

    @Column(name = "LOCATION_X", nullable = false, columnDefinition = "integer")
    private int LOCATION_X;

    @Column(name = "LOCATION_Y", nullable = false, columnDefinition = "integer")
    private int LOCATION_Y;

}
