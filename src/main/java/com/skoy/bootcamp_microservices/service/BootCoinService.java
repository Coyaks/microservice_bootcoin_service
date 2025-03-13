package com.skoy.bootcamp_microservices.service;

import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import com.skoy.bootcamp_microservices.repository.IBootCoinRepository;
import com.skoy.bootcamp_microservices.repository.IBootCoinTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BootCoinService implements IBootCoinService {

    private final IBootCoinRepository bootCoinRepository;
    private final IBootCoinTransactionRepository transactionRepository;
    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(BootCoinService.class);

    @Value("${services.customer}")
    private String customerServiceUrl;

    @Value("${services.bankaccount}")
    private String bankAccountServiceUrl;

    @Override
    public Mono<BootCoinWallet> createWallet(BootCoinWallet bootCoinWallet) {
        return bootCoinRepository.save(bootCoinWallet);
    }

    @Override
    public Mono<BootCoinWallet> getWalletByPhoneNumber(String phoneNumber) {
        return bootCoinRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Mono<BootCoinTransaction> requestPurchase(BootCoinTransaction transaction) {
        transaction.setTransactionStatus("Pendiente");
        return transactionRepository.save(transaction);
    }

    @Override
    public Mono<BootCoinTransaction> completeTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .flatMap(transaction -> {
                    transaction.setTransactionStatus("Completado");
                    return transactionRepository.save(transaction);
                });
    }
}