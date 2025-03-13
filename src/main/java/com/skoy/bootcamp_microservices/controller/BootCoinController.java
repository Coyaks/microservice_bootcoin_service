package com.skoy.bootcamp_microservices.controller;

import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import com.skoy.bootcamp_microservices.model.request.AssociateCardRequest;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import com.skoy.bootcamp_microservices.service.IBootCoinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/bootcoin")
public class BootCoinController {

    private static final Logger logger = LoggerFactory.getLogger(BootCoinController.class);

    @Autowired
    private IBootCoinService bootCoinService;

    @PostMapping("/create-wallet")
    public Mono<ResponseEntity<BootCoinWallet>> createWallet(@RequestBody BootCoinWallet wallet) {
        return bootCoinService.createWallet(wallet)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/wallet/{phoneNumber}")
    public Mono<ResponseEntity<BootCoinWallet>> getWallet(@PathVariable String phoneNumber) {
        return bootCoinService.getWalletByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/request-purchase")
    public Mono<ResponseEntity<BootCoinTransaction>> requestPurchase(@RequestBody BootCoinTransaction transaction) {
        return bootCoinService.requestPurchase(transaction)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/complete-transaction/{transactionId}")
    public Mono<ResponseEntity<BootCoinTransaction>> completeTransaction(@PathVariable String transactionId) {
        return bootCoinService.completeTransaction(transactionId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
