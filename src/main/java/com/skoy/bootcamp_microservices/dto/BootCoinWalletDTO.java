package com.skoy.bootcamp_microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootCoinWalletDTO {
    private String id;
    private String documentNumber; // DNI, CEX, Pasaporte
    private String phoneNumber;
    private String email;
    private BigDecimal balance = BigDecimal.ZERO;
}
