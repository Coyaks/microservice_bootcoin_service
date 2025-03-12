package com.skoy.bootcamp_microservices.repository;

import com.skoy.bootcamp_microservices.model.Wallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IWalletRepository extends ReactiveMongoRepository<Wallet, String> {
    Mono<Wallet> findByPhoneNumber(String phoneNumber);
}
