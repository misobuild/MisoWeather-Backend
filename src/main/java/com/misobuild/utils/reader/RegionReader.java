package com.misobuild.utils.reader;

import com.misobuild.domain.region.Region;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * rawScaleList를 필터링하여 필요한 정보만 전달할 수 있게 합니다.
 *
 * @author yeon
 **/
@Component
public class RegionReader {

    /**
     * rawMidScaleList에서 smallScale이 다르고 midScale이 중복된 결과를 삭제한 리스트를 반환합니다.
     *
     * @author yeon
     **/
    public List<Region> filterMidScaleList(List<Region> rawMidScaleList) {
        return rawMidScaleList.stream()
                .filter(distinctByKey(Region::getMidScale))
                .sorted(Comparator.comparing(Region::getId))
                .collect(Collectors.toList());
    }

    /**
     * 스트림 람다에서 활용할 정적 메소드를 정의합니다.
     *
     * @author yeon
     **/
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(); // parallel stream에서 호출할까봐 이렇게 한듯.
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
