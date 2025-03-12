package com.skoy.bootcamp_microservices.service;

import com.skoy.bootcamp_microservices.dto.CardDTO;
import com.skoy.bootcamp_microservices.model.Wallet;
import com.skoy.bootcamp_microservices.model.request.MakePaymentRequest;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IWalletService {

    Mono<Wallet> createWallet(Wallet wallet);
    Mono<Wallet> findByPhoneNumber(String phoneNumber);
    Mono<String> transferMoney(TransferRequest request);
    Mono<Wallet> associateCard(String phoneNumber, String cardNumber);
    Flux<Wallet> getAllWallets();
}
