package com.misobuild.utils.builder;


import com.misobuild.utils.time.VillageForecastTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class VillageForecastApiUrlBuilder extends ApiUrlBuilder {
    int pageNo = 1;
    int numOfRows = 12*48 + 4;
    String urlBase = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

    @Builder
    public VillageForecastApiUrlBuilder(VillageForecastTime villageForecastTime, String serviceKey,
                                        int locationX, int locationY) {
        super();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.urlBase).append("?")
                .append("serviceKey=").append(serviceKey)
                .append("&pageNo=").append(this.pageNo)
                .append("&numOfRows=").append(this.numOfRows)
                .append("&dataType=").append(super.dataType)
                .append("&base_date=")
                .append(villageForecastTime.getBaseDateYear())
                .append(villageForecastTime.getBaseDateMonth())
                .append(villageForecastTime.getBaseDateDay())
                .append("&base_time=").append(villageForecastTime.getBaseTime())
                .append("&nx=").append(locationX)
                .append("&ny=").append(locationY);

        super.urlLink = stringBuilder.toString();
    }
}
