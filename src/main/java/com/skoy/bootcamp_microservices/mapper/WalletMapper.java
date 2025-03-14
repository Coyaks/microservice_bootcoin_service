package com.skoy.bootcamp_microservices.mapper;

import com.skoy.bootcamp_microservices.dto.BootCoinWalletDTO;
import com.skoy.bootcamp_microservices.model.BootCoinWallet;

public class WalletMapper {

    public static BootCoinWallet toEntity(BootCoinWalletDTO dto) {
        BootCoinWallet item = new BootCoinWallet();
        item.setId(dto.getId());
        /*item.setBankAccountId(dto.getBankAccountId());
        item.setMainBankAccountId(dto.getMainBankAccountId());
        item.setCardType(dto.getCardType());
        item.setCardHolderName(dto.getCardHolderName());
        item.setCardNumber(dto.getCardNumber());
        item.setExpirationDate(dto.getExpirationDate());
        item.setCvv(dto.getCvv());
        item.setStatus(dto.getStatus());*/
        return item;
    }

    public static BootCoinWalletDTO toDto(BootCoinWallet item) {
        BootCoinWalletDTO dto = new BootCoinWalletDTO();
        dto.setId(item.getId());
        /*dto.setBankAccountId(item.getBankAccountId());
        dto.setMainBankAccountId(item.getMainBankAccountId());
        dto.setCardType(item.getCardType());
        dto.setCardHolderName(item.getCardHolderName());
        dto.setCardNumber(item.getCardNumber());
        dto.setExpirationDate(item.getExpirationDate());
        dto.setCvv(item.getCvv());
        dto.setStatus(item.getStatus());
        dto.setCreatedAt(item.getCreatedAt());*/
        return dto;
    }
}
