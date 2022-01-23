package com.misobuild.dto.response.survey;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyResult {
    private Long surveyId;
    private String question;
    private String topFirst;
    private String topSecond;
    private String topThird;
    private int topFirstValue;
    private int topSecondValue;
    private int topThirdValue;

    @Builder
    public SurveyResult(Long surveyId, String question, String topFirst, String topSecond, String topThird, int topFirstValue, int topSecondValue, int topThirdValue) {
        this.surveyId = surveyId;
        this.question = question;
        this.topFirst = topFirst;
        this.topSecond = topSecond;
        this.topThird = topThird;
        this.topFirstValue = topFirstValue;
        this.topSecondValue = topSecondValue;
        this.topThirdValue = topThirdValue;
    }
}
