package com.crypto.simulator.backend.model.dtos;

import java.math.BigDecimal;

public class CryptoHoldingForProfileDTO {
    private Integer userId;
    private String cryptoSymbol;
    private BigDecimal quantity;
    private String cryptoName;

    public CryptoHoldingForProfileDTO() {
    }

    public CryptoHoldingForProfileDTO(Integer userId, String cryptoSymbol, BigDecimal quantity, String cryptoName) {
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.quantity = quantity;
        this.cryptoName = cryptoName;
    }

    public CryptoHoldingForProfileDTO(Integer userId, String cryptoSymbol, BigDecimal quantity) {
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.quantity = quantity;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCryptoSymbol() {
        return this.cryptoSymbol;
    }

    public void setCryptoSymbol(String cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getCryptoName() {
        return this.cryptoName;
    }

    public void setName(String cryptoName) {
        this.cryptoName = cryptoName;
    }
}
