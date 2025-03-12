package com.skoy.bootcamp_microservices.service;

import com.skoy.bootcamp_microservices.model.Wallet;
import com.skoy.bootcamp_microservices.model.request.TransferRequest;
import com.skoy.bootcamp_microservices.repository.IWalletRepository;
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
public class WalletService implements IWalletService {

    private final IWalletRepository walletRepository;
    private final WebClient.Builder webClientBuilder;
    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);

    @Value("${services.customer}")
    private String customerServiceUrl;

    @Value("${services.bankaccount}")
    private String bankAccountServiceUrl;


    @Override
    public Mono<Wallet> createWallet(Wallet wallet) {
        return walletRepository.findByPhoneNumber(wallet.getPhoneNumber())
                .flatMap(existingWallet -> Mono.error(new RuntimeException("El monedero ya existe.")))
                .switchIfEmpty(walletRepository.save(wallet))
                .cast(Wallet.class);
    }

    @Override
    public Mono<Wallet> findByPhoneNumber(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Mono<String> transferMoney(TransferRequest request) {
        return Mono.zip(
                walletRepository.findByPhoneNumber(request.getSenderPhoneNumber()),
                walletRepository.findByPhoneNumber(request.getReceiverPhoneNumber())
        ).flatMap(tuple -> {
            Wallet sender = tuple.getT1();
            Wallet receiver = tuple.getT2();

            if (sender.getBalance().compareTo(request.getAmount()) < 0) {
                return Mono.error(new RuntimeException("Saldo insuficiente."));
            }

            sender.setBalance(sender.getBalance().subtract(request.getAmount()));
            receiver.setBalance(receiver.getBalance().add(request.getAmount()));

            return Mono.when(walletRepository.save(sender), walletRepository.save(receiver))
                    .thenReturn("Transferencia exitosa.");
        });
    }

    @Override
    public Mono<Wallet> associateCard(String phoneNumber, String cardNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .flatMap(wallet -> {
                    wallet.setAssociatedCardNumber(cardNumber);
                    return walletRepository.save(wallet);
                });
    }

    @Override
    public Flux<Wallet> getAllWallets() {
        return walletRepository.findAll();
    }
}