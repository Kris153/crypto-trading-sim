package com.crypto.simulator.backend.model;

import java.math.BigDecimal;

public class CryptoHolding {
    private Integer id;
    private Integer userId;
    private String cryptoSymbol;
    private BigDecimal quantity;

    public CryptoHolding() {
    }

    public CryptoHolding(Integer id, Integer userId, String cryptoSymbol, BigDecimal quantity) {
        this.id = id;
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.quantity = quantity;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
