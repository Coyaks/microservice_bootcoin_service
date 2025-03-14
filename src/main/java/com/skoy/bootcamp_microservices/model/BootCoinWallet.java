package com.skoy.bootcamp_microservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "bootcoin_wallets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootCoinWallet {
    @Id
    private String id;
    private String documentNumber; // DNI, CEX, Pasaporte
    private String phoneNumber;
    private String email;
    private BigDecimal balance = BigDecimal.ZERO;
}