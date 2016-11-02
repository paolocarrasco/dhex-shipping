package com.dhex.shipping.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jorge Santa Cruz on 23/10/2016.
 */
public enum ActivityIndicatorEnum {
    ENABLE(1L), DISABLE(0L), ALL(-1L);

    private Long id;
    private ActivityIndicatorEnum(long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static Long getIdByBooleanStatus(boolean status){
        return status ? 1L : 0L;
    }

    public static ActivityIndicatorEnum getActivityIndicatorEnumById(Long id) {
        List<ActivityIndicatorEnum> list = Arrays.asList(ActivityIndicatorEnum.values());
        list = list
                .stream()
                .filter(a -> a.getId().compareTo(id) == 0)
                .collect(Collectors.toList());
        return list.size() > 0 ? list.get(0) : ActivityIndicatorEnum.ALL;
    }
}
