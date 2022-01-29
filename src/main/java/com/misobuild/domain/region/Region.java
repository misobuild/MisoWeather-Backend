package com.misobuild.domain.region;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DynamicUpdate
@Entity(name = "REGION_TB")
public class Region {
    @Id
    @Column(name = "REGION_ID")
    private Long id;

    @Column(name = "BIGSCALE", nullable = false, columnDefinition = "varchar(45)")
    private String bigScale;

    @Column(name = "MIDSCALE", nullable = false, columnDefinition = "varchar(45)")
    private String midScale;

    @Column(name = "SMALLSCALE", nullable = false, columnDefinition = "varchar(45)")
    private String smallScale;

    @JsonIgnore
    @Column(name = "LOCATION_X", nullable = false, columnDefinition = "integer")
    private int LOCATION_X;

    @JsonIgnore
    @Column(name = "LOCATION_Y", nullable = false, columnDefinition = "integer")
    private int LOCATION_Y;

    @Column(name = "LATITUDE", nullable = false, columnDefinition = "integer")
    @JsonIgnore
    private int latitude;

    @Column(name = "LONGITUDE", nullable = false, columnDefinition = "integer")
    @JsonIgnore
    private int longitude;

    @Column(name = "LAST_WEATHER_UPDATE")
    @JsonIgnore
    private LocalDateTime lastWeatherUpdate;

    public Region update(LocalDateTime modifiedDateTime){
        this.lastWeatherUpdate = modifiedDateTime;
        return this;
    }
}
