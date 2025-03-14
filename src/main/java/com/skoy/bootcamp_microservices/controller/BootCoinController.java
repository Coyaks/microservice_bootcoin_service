package com.skoy.bootcamp_microservices.controller;

import com.skoy.bootcamp_microservices.model.BootCoinTransaction;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;
import com.skoy.bootcamp_microservices.service.IBootCoinService;
import com.skoy.bootcamp_microservices.utils.ApiResponse;
import com.skoy.bootcamp_microservices.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public Flux<BootCoinWallet> findAll() {
        return bootCoinService.findAll();
    }

    @GetMapping("/transactions")
    public Flux<BootCoinTransaction> findAllTransactions() {
        return bootCoinService.findAllTransactions();
    }

    @PostMapping
    public Mono<ResponseEntity<BootCoinWallet>> createWallet(@RequestBody BootCoinWallet wallet) {
        return bootCoinService.createWallet(wallet)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ApiResponse<Void>> delete(@PathVariable String id) {
        logger.info("Deleting item with ID: {}", id);
        return bootCoinService.findById(id)
                .flatMap(existingCustomer -> bootCoinService.delete(id)
                        .then(Mono.just(new ApiResponse<Void>("Eliminado correctamente", null, Constants.STATUS_OK))))
                .switchIfEmpty(Mono.just(new ApiResponse<Void>("ID no encontrado", null, Constants.STATUS_E404)))
                .onErrorResume(e -> {
                    logger.error("Error deleting item with ID: {}", id, e);
                    return Mono.just(new ApiResponse<Void>("Error al eliminar", null, Constants.STATUS_E500));
                });
    }

    @DeleteMapping("/transactions/{id}")
    public Mono<ApiResponse<Void>> deleteTransaction(@PathVariable String id) {
        logger.info("Deleting item with ID: {}", id);
        return bootCoinService.findByIdTransaction(id)
                .flatMap(existingCustomer -> bootCoinService.deleteTransaction(id)
                        .then(Mono.just(new ApiResponse<Void>("Eliminado correctamente", null, Constants.STATUS_OK))))
                .switchIfEmpty(Mono.just(new ApiResponse<Void>("ID no encontrado", null, Constants.STATUS_E404)))
                .onErrorResume(e -> {
                    logger.error("Error deleting item with ID: {}", id, e);
                    return Mono.just(new ApiResponse<Void>("Error al eliminar", null, Constants.STATUS_E500));
                });
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

    @PutMapping("/accept-purchase/{transactionId}")
    public Mono<ResponseEntity<BootCoinTransaction>> acceptPurchaseRequest(@PathVariable String transactionId, @RequestParam String sellerPhoneNumber) {
        return bootCoinService.acceptPurchaseRequest(transactionId, sellerPhoneNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/validate-and-process/{transactionNumber}")
    public Mono<ResponseEntity<BootCoinTransaction>> validateAndProcessPayment(@PathVariable String transactionNumber) {
        return bootCoinService.validateAndProcessPayment(transactionNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
