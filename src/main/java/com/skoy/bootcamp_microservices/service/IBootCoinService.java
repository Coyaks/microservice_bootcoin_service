package com.skoy.bootcamp_microservices.service;

import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootCoinService {

    Flux<BootCoinWallet> findAll();

    Flux<BootCoinTransaction> findAllTransactions();
    Mono<Void> delete(String id);
    Mono<Void> deleteTransaction(String id);
    Mono<BootCoinWallet> findById(String id);
    Mono<BootCoinTransaction> findByIdTransaction(String id);

    Mono<BootCoinWallet> createWallet(BootCoinWallet bootCoinWallet);
    //Mono<BootCoinWallet> getWalletByPhoneNumber(String phoneNumber);
    Mono<BootCoinWallet> findByPhoneNumber(String phoneNumber);
    Mono<BootCoinTransaction> requestPurchase(BootCoinTransaction transaction);

    Mono<BootCoinTransaction> acceptPurchaseRequest(String transactionId, String sellerPhoneNumber);
    Mono<BootCoinTransaction> validateAndProcessPayment(String transactionNumber);

}
