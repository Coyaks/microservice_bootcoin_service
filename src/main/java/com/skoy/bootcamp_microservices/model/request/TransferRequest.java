package com.skoy.bootcamp_microservices.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String senderPhoneNumber;
    private String receiverPhoneNumber;
    private BigDecimal amount;
}
