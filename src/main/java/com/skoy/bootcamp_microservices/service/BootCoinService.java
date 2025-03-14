package com.skoy.bootcamp_microservices.service;

import com.skoy.bootcamp_microservices.enums.TransactionStatusEnum;
import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import com.skoy.bootcamp_microservices.repository.IBootCoinRepository;
import com.skoy.bootcamp_microservices.repository.IBootCoinTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

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

    //REDIS
    private final ReactiveRedisTemplate<String, BootCoinWallet> redisTemplate;
    private static final String WALLET_CACHE_PREFIX = "wallet:";


    @Override
    public Mono<BootCoinWallet> createWallet(BootCoinWallet bootCoinWallet) {
        return bootCoinRepository.save(bootCoinWallet);
    }

    /*@Override
    public Mono<BootCoinWallet> getWalletByPhoneNumber(String phoneNumber) {
        return bootCoinRepository.findByPhoneNumber(phoneNumber);
    }*/


   /* @Override
    public Mono<BootCoinWallet> getWalletByPhoneNumber(String phoneNumber) {
        String cacheKey = WALLET_CACHE_PREFIX + phoneNumber;
        return redisTemplate.opsForValue().get(cacheKey)
                .switchIfEmpty(bootCoinRepository.findByPhoneNumber(phoneNumber)
                        .flatMap(wallet -> redisTemplate.opsForValue().set(cacheKey, wallet).thenReturn(wallet)));
    }*/


    @Override
    public Mono<BootCoinWallet> getWalletByPhoneNumber(String phoneNumber) {
        ReactiveValueOperations<String, BootCoinWallet> ops = redisTemplate.opsForValue();
        String cacheKey = "wallet:" + phoneNumber;

        return ops.get(cacheKey)
                .switchIfEmpty(bootCoinRepository.findByPhoneNumber(phoneNumber)
                        .flatMap(wallet -> ops.set(cacheKey, wallet).thenReturn(wallet))
                );
    }

    @Override
    public Mono<BootCoinTransaction> requestPurchase(BootCoinTransaction transaction) {
        return Mono.zip(
                        bootCoinRepository.findByPhoneNumber(transaction.getBuyerPhoneNumber()),
                        bootCoinRepository.findByPhoneNumber(transaction.getSellerPhoneNumber())
                )
                .flatMap(tuple -> {
                    transaction.setTransactionStatus(TransactionStatusEnum.PENDING);
                    return transactionRepository.save(transaction);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Números de teléfono no encontrados")));
    }


    @Override
    public Flux<BootCoinWallet> findAll() {
        return bootCoinRepository.findAll();
    }

    @Override
    public Mono<BootCoinWallet> findByPhoneNumber(String phoneNumber) {
        return bootCoinRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Flux<BootCoinTransaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Mono<Void> delete(String id) {
        return bootCoinRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteTransaction(String id) {
        return transactionRepository.deleteById(id);
    }

    @Override
    public Mono<BootCoinWallet> findById(String id) {
        return bootCoinRepository.findById(id);
    }

    @Override
    public Mono<BootCoinTransaction> findByIdTransaction(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Mono<BootCoinTransaction> acceptPurchaseRequest(String transactionId, String sellerPhoneNumber) {
        return transactionRepository.findById(transactionId)
                .flatMap(transaction -> {
                    transaction.setSellerPhoneNumber(sellerPhoneNumber);
                    transaction.setTransactionStatus(TransactionStatusEnum.COMPLETED);
                    transaction.setTransactionNumber(UUID.randomUUID().toString());
                    return transactionRepository.save(transaction);
                });
    }


    @Override
    public Mono<BootCoinTransaction> validateAndProcessPayment(String transactionNumber) {
        return transactionRepository.findByTransactionNumber(transactionNumber)
                .flatMap(transaction -> {
                    if (isValidTransaction(transaction)) {
                        transaction.setTransactionStatus(TransactionStatusEnum.COMPLETED);
                        return transactionRepository.save(transaction);
                    } else {
                        return Mono.error(new IllegalArgumentException("Datos de la operación no válidos"));
                    }
                });
    }

    private boolean isValidTransaction(BootCoinTransaction transaction) {
        // Validar monto, modo de pago, número de cuenta o de celular
        return transaction.getAmount() != null && transaction.getAmount() > 0 &&
                transaction.getPaymentMethod() != null &&
                transaction.getBuyerPhoneNumber() != null && !transaction.getBuyerPhoneNumber().isEmpty();
    }

}