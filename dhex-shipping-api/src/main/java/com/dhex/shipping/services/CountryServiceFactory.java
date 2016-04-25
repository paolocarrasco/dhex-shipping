package com.dhex.shipping.services;

public final class CountryServiceFactory {
    private CountryServiceFactory() {
    }

    public static CountryService create() {
        return new LoggingCountryService();
    }
}
