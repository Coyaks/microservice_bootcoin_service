package com.skoy.bootcamp_microservices.repository;

import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IBootCoinTransactionRepository extends ReactiveMongoRepository<BootCoinTransaction, String> {
    Mono<BootCoinTransaction> findById(String id);
    Mono<BootCoinTransaction> findByTransactionNumber(String transactionNumber);
}
