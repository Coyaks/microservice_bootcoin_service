package com.skoy.bootcamp_microservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "wallets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    private String id;
    private String documentNumber; // DNI, CEX, Pasaporte
    private String phoneNumber; // Número de celular (clave primaria)
    private String email;
    private String imei;
    private BigDecimal balance = BigDecimal.ZERO; // Saldo inicial en 0
    private String associatedCardNumber; // Tarjeta de débito asociada (opcional)
}