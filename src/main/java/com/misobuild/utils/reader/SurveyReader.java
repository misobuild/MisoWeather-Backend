package com.misobuild.utils.reader;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misobuild.domain.member_survey_mapping.MemberSurveyMapping;
import lombok.Builder;
import lombok.Getter;

import java.util.*;

@Getter
public class SurveyReader {
    @JsonIgnore
    private HashMap<String, Integer> infoMap;
    @JsonIgnore
    private List<MemberSurveyMapping> memberSurveyMappingList;

    private List<String> keyList;
    private List<Integer> valueList;
    private Long surveyId;
    private String surveyTitle;
    private String surveyDescription;

    @Builder
    public SurveyReader(List<MemberSurveyMapping> msmList, Long surveyId, String surveyDescription, String surveyTitle) {
        this.memberSurveyMappingList = msmList;
        this.surveyId = surveyId;
        this.infoMap = new HashMap<>();
        this.keyList = new ArrayList<>();
        this.valueList = new ArrayList<>();
        this.surveyDescription = surveyDescription;
        this.surveyTitle = surveyTitle;
    }

    public void setInfoMap() {
        for (MemberSurveyMapping msm : memberSurveyMappingList) {
            int count = 1;
            String answer = msm.getAnswer().getAnswer();
            if (this.infoMap.containsKey(answer)) count = this.infoMap.get(answer) + 1;
            this.infoMap.put(answer, count);
        }
    }

    public void setValues() {
        List<Map.Entry<String, Integer>> tempList = new ArrayList<>(infoMap.entrySet());
        tempList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        for (Map.Entry<String, Integer> result : tempList) {
            float answerValue = ((float) result.getValue() / memberSurveyMappingList.size()) * 100;
            keyList.add(result.getKey());
            valueList.add((int) answerValue);
        }
        int repeatValue = 3 - infoMap.size();
        for (int i = 0; i < repeatValue; i++) {
            keyList.add(null);
            valueList.add(0);
        }
    }
}
