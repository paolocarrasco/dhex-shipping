package com.dhex.shipping.model;

public class ShippingRequest {

    private long code;
    private Status status;

    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status currentStatus() {
        return status;
    }
}
