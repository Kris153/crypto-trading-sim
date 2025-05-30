package com.crypto.simulator.backend.service;

import com.crypto.simulator.backend.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface CurrencyService {
    String getCurrencyNameBySymbol(String symbol);
    BigDecimal getCurrencyQuantityBySymbol(String symbol, User currentUser) throws SQLException;
}
