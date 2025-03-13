package com.skoy.bootcamp_microservices.repository;

import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IBootCoinRepository extends ReactiveMongoRepository<BootCoinWallet, String> {
    Mono<BootCoinWallet> findByPhoneNumber(String phoneNumber);
}
