package com.crypto.simulator.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Integer id;
    private Integer userId;
    private String cryptoSymbol;
    private String cryptoName;
    private LocalDateTime timestamp;
    private boolean isBuy;
    private BigDecimal quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalValue;
    private BigDecimal balanceAfter;

    public Transaction() {
    }

    public String getCryptoName() {
        return this.cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public Transaction(Integer id, Integer userId, String cryptoSymbol, String cryptoName, LocalDateTime timestamp, boolean isBuy, BigDecimal quantity, BigDecimal pricePerUnit, BigDecimal totalValue, BigDecimal balanceAfter) {
        this.id = id;
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.cryptoName = cryptoName;
        this.timestamp = timestamp;
        this.isBuy = isBuy;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalValue = totalValue;
        this.balanceAfter = balanceAfter;
    }

    public Transaction(Integer userId, String cryptoSymbol, LocalDateTime timestamp, boolean isBuy, BigDecimal quantity, BigDecimal pricePerUnit, BigDecimal totalValue, BigDecimal balanceAfter) {
        this.userId = userId;
        this.cryptoSymbol = cryptoSymbol;
        this.timestamp = timestamp;
        this.isBuy = isBuy;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalValue = totalValue;
        this.balanceAfter = balanceAfter;
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

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isBuy() {
        return this.isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public BigDecimal getQuantity() {
        return this.quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerUnit() {
        return this.pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigDecimal getTotalValue() {
        return this.totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getBalanceAfter() {
        return this.balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }
}
