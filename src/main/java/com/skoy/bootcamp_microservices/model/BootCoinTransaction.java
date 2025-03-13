package com.skoy.bootcamp_microservices.model;

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
    private String paymentMethod; // "Yanki" o "Transferencia"
    private String transactionStatus; // "Pendiente", "Completado", "Cancelado"
}
