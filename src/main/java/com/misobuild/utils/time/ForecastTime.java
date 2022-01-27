package com.misobuild.utils.time;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// TODO 추상 클래스를 쓸 거면 추상 클래스를 쓰고, 인터페이스를 쓸 거면 쓰고, 상속이면 상속으로 쓰고
// TODO 개인적으로는 composition 방식으로 가야한다고 생각한다.
@Getter
@NoArgsConstructor
public abstract class ForecastTime {
    public LocalDateTime lastAnnounceTime;
    public String baseDateYear;
    public String baseDateMonth;
    public String baseDateDay;
    public String baseTime;


    public String getMonth(LocalDateTime dateTime){
        return dateTime.getMonth().getValue()
                < 10
                ? "0" + dateTime.getMonth().getValue()
                : String.valueOf(dateTime.getMonth().getValue());
    }

    public String getDayOfMonth(LocalDateTime dateTime){
        return dateTime.getDayOfMonth()
                < 10
                ? "0" + dateTime.getDayOfMonth()
                : String.valueOf(dateTime.getDayOfMonth());
    }

    public String getTime(LocalDateTime dateTime){
        return dateTime.getHour() < 10
                ? "0" + dateTime.getHour() + "00" : dateTime.getHour() + "00";
    }
}
