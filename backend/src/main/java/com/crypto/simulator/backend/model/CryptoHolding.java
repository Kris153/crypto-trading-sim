package com.crypto.simulator.backend.model;

import java.math.BigDecimal;

public class CryptoHolding {
    private Long id;
    private Long userId;
    private String cryptoSymbol;
    private BigDecimal quantity;

    public CryptoHolding() {
    }

    public CryptoHolding(Long id, Long userId, String cryptoSymbol, BigDecimal quantity) {
        this.id = id;
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
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
}
