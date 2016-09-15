package com.dhex.shipping.dtos;

import java.util.List;

public class ListOf<T> {

    private List<T> list;

    private ListOf(List<T> list) {
        this.list = list;
    }

    public static <T> ListOf<T> createListOf(List<T> list) {
        return new ListOf<>(list);
    }

    public List<T> getList() {
        return list;
    }
}
