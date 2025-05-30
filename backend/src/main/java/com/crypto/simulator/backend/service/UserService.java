package com.crypto.simulator.backend.service;

import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.model.dtos.UserRegisterDTO;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface UserService {
    void register(UserRegisterDTO registerData) throws SQLException;

    boolean confirmPassword(UserRegisterDTO registerData);

    boolean doUsernameExists(UserRegisterDTO registerData) throws SQLException;
    User getCurrentUser() throws SQLException;
    boolean hasEnoughMoneyToBuy(String currencySymbol, BigDecimal currentPrice, BigDecimal quantity) throws SQLException;

    boolean buyCrypto(String currencySymbol, BigDecimal currentPrice, BigDecimal quantity) throws SQLException;
    boolean hasEnoughQuantity(String currencySymbol, BigDecimal quantity) throws SQLException;
    boolean sellCrypto(String currencySymbol, BigDecimal currentPrice, BigDecimal quantity) throws SQLException;

    boolean resetBalance() throws SQLException;
}
