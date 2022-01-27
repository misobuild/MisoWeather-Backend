package com.misobuild.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CurrentForecastTime extends ForecastTime{
    LocalDateTime dateTime;

    public CurrentForecastTime() {
        setDateTime();
        super.baseDateYear = String.valueOf(LocalDate.now().getYear());
        super.baseDateMonth = getMonth(dateTime);
        super.baseDateDay = getDayOfMonth(dateTime);
        super.baseTime = getTime(dateTime);
    }

    public void setDateTime(){
        dateTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), 0));
    }
}
