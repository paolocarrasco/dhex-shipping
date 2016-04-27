package com.dhex.shipping.builders;

import com.dhex.shipping.model.Country;
import com.dhex.shipping.model.ShippingStatus;
import com.dhex.shipping.model.Status;

public class ShippingStatusBuilder {
    private Status status = Status.SHIPPED;
    private Country countryOfStatus = new CountryBuilder().create();
    private String description = "A description";

    public ShippingStatusBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public ShippingStatusBuilder setCountry(Country countryOfStatus) {
        this.countryOfStatus = countryOfStatus;
        return this;
    }

    public ShippingStatusBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ShippingStatus create() {
        return new ShippingStatus(status, countryOfStatus, description);
    }
}