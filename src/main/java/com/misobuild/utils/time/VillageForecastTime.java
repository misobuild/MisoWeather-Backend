package com.misobuild.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class VillageForecastTime extends ForecastTime{
    public int lastAnnounceHour = 23;
    public int lastAnnounceMinute = 10;

    public VillageForecastTime() {
        LocalDateTime lastAnnounceTime = LocalDateTime
                .of(LocalDate.now().minusDays(1L), LocalTime.of(this.lastAnnounceHour, this.lastAnnounceMinute));

        super.lastAnnounceTime = lastAnnounceTime;
        super.baseDateYear = String.valueOf(LocalDate.now().getYear());
        super.baseDateMonth = getMonth(lastAnnounceTime);
        super.baseDateDay = getDayOfMonth(lastAnnounceTime);
        super.baseTime = getTime(lastAnnounceTime);
    }
}
