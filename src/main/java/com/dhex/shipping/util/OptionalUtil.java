package com.dhex.shipping.util;

import java.util.Optional;

/**
 * Created by Jorge Santa Cruz on 30/10/2016.
 */
public class OptionalUtil{

    public static boolean isPresent(Object object){
        return Optional.ofNullable(object).isPresent();
    }

    public static boolean isNotPresent(Object object){
        return !isPresent(object);
    }
}
