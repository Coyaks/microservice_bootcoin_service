package com.skoy.bootcamp_microservices.enums;

public enum PaymentMethodEnum {
    YANKI("Yanki"),
    TRANSFER("Transferencia"),;

    private final String name;

    PaymentMethodEnum(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
