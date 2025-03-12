package com.skoy.bootcamp_microservices.mapper;

import com.skoy.bootcamp_microservices.dto.CardDTO;
import com.skoy.bootcamp_microservices.model.Wallet;

public class WalletMapper {

    public static Wallet toEntity(CardDTO dto) {
        Wallet item = new Wallet();
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

    public static CardDTO toDto(Wallet item) {
        CardDTO dto = new CardDTO();
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
