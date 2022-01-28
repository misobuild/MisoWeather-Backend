package com.misobuild.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO 단순 리스트 반환에 아래를 사용하여 리팩토링하자.
@Getter
@NoArgsConstructor
public class ListDto<T> {
    private List<T> responseList;

    @Builder
    public ListDto(List<T> responseList) {
        this.responseList = responseList;
    }
}
