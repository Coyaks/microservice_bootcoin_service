package com.skoy.bootcamp_microservices.controller;

import com.skoy.bootcamp_microservices.model.Wallet;
import com.skoy.bootcamp_microservices.model.request.AssociateCardRequest;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import com.skoy.bootcamp_microservices.service.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private IWalletService walletService;

    @GetMapping
    public Flux<Wallet> getAllWallets() {
        return walletService.getAllWallets();
    }

    @PostMapping
    public Mono<ResponseEntity<Wallet>> createWallet(@RequestBody Wallet wallet) {
        return walletService.createWallet(wallet)
                .map(savedWallet -> ResponseEntity.status(HttpStatus.CREATED).body(savedWallet))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{phoneNumber}")
    public Mono<ResponseEntity<Wallet>> getWallet(@PathVariable String phoneNumber) {
        return walletService.findByPhoneNumber(phoneNumber)
                .map(wallet -> ResponseEntity.ok(wallet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/transfer")
    public Mono<ResponseEntity<String>> transferMoney(@RequestBody TransferRequest request) {
        return walletService.transferMoney(request)
                .map(response -> ResponseEntity.ok(response))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PostMapping("/{phoneNumber}/associate-card")
    public Mono<ResponseEntity<Wallet>> associateCard(@PathVariable String phoneNumber, @RequestBody AssociateCardRequest request) {
        return walletService.associateCard(phoneNumber, request.getCardNumber())
                .map(wallet -> ResponseEntity.ok(wallet))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
