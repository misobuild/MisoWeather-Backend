package com.misobuild.utils.factory;

import com.misobuild.domain.region.Region;
import com.misobuild.utils.updater.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

// TODO Daily를 업데이트 할 때만 lastweatherupdate를 업데이트한다.
// TODO 한나절이 지나기 전까지는 current만 업데이트한다.
// TODO 한나절이 지나면 current, hourly 업데이트한다
// TODO 두나절이 지나면 current, houly, daily 업데이트한다

@Component
@RequiredArgsConstructor
public class UpdaterFactory {
    private final CurrentUpdater currentUpdater;
    private final CurrentHourlyUpdater currentHoulyUpdater;
    private final TotalUpdater totalUpdater;
    private final NoneUpdater noneUpdater;

    public Updater of(Region region){
        LocalDateTime lastUpdate = region.getLastWeatherUpdate();
        List<Integer> timeGapList =  Arrays.asList(6, 12);
        LocalDateTime lastWeatherUpdateWithoutMinutes = LocalDateTime
                .of(LocalDate.of(lastUpdate.getYear(), lastUpdate.getMonth(), lastUpdate.getDayOfMonth()), LocalTime.of(lastUpdate.getHour(), 0, 0));
        LocalDateTime currentTimeWithoutMinutes = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalDateTime.now().getHour(), 0, 0));
        Integer timeGap = Math.toIntExact(ChronoUnit.HOURS.between(lastWeatherUpdateWithoutMinutes, currentTimeWithoutMinutes));
        if(timeGap == 0) return noneUpdater;
        switch (timeGapList.stream().filter(item -> item > timeGap).findFirst().orElse(0)){
            case 6:
                return currentUpdater;
            default:
                return totalUpdater;
        }
    }
}
