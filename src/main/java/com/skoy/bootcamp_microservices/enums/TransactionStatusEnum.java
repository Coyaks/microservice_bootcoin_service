package com.skoy.bootcamp_microservices.enums;

public enum TransactionStatusEnum {
    PENDING("Pendiente"),
    COMPLETED("Completado"),
    CANCELLED("Cancelado");

    private final String name;

    TransactionStatusEnum(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
