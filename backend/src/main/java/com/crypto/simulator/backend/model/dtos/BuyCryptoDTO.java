package com.crypto.simulator.backend.model.dtos;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class BuyCryptoDTO {
    @Positive
    BigDecimal quantity;

    String cryptoSymbol;

    BigDecimal price;
}
