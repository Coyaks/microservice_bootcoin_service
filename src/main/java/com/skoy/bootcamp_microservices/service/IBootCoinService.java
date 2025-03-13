package com.skoy.bootcamp_microservices.service;

import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootCoinService {

    Mono<BootCoinWallet> createWallet(BootCoinWallet bootCoinWallet);
    Mono<BootCoinWallet> getWalletByPhoneNumber(String phoneNumber);
    Mono<BootCoinTransaction> requestPurchase(BootCoinTransaction transaction);
    Mono<BootCoinTransaction> completeTransaction(String transactionId);

}
