package com.dhex.shipping.model;

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
}
