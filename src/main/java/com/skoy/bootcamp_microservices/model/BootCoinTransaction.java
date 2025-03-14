package com.skoy.bootcamp_microservices.model;

import com.skoy.bootcamp_microservices.enums.PaymentMethodEnum;
import com.skoy.bootcamp_microservices.enums.TransactionStatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bootcoin_transactions")
@Data
public class BootCoinTransaction {
    @Id
    private String id;
    private String buyerPhoneNumber;
    private String sellerPhoneNumber;
    private Double amount;
    private PaymentMethodEnum paymentMethod;
    private TransactionStatusEnum transactionStatus;
    private String transactionNumber;
}
