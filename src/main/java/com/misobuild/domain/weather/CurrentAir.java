package com.misobuild.domain.weather;

import com.misobuild.constants.BigScaleEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@RedisHash("currentAir")
public class  CurrentAir implements Serializable {

    @Id
    @Indexed
    private Long key;

    private int pm10;
    private int pm25;
    private String pm10Grade;
    private String pm25Grade;
    private String bigScale;

    @TimeToLive
    private Long expiration;

    @Builder
    public CurrentAir(int pm10, int pm25, String pm10Grade, String pm25Grade, String bigScale) {
        this.key = BigScaleEnum.getEnumByShortValues(bigScale).getRedisKey();
        this.expiration = 3600L;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.pm10Grade = pm10Grade;
        this.pm25Grade = pm25Grade;
        this.bigScale = bigScale;
    }
}
