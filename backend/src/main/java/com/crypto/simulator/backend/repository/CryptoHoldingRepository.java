package com.crypto.simulator.backend.repository;

import com.crypto.simulator.backend.model.CryptoHolding;
import com.crypto.simulator.backend.model.dtos.CryptoHoldingForProfileDTO;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface CryptoHoldingRepository {
    void saveCryptoHolding(CryptoHolding holding) throws SQLException;
    void updateCryptoHoldings(Integer userId, String currencySymbol, BigDecimal amount) throws SQLException;
    BigDecimal getAmountOfCryptoForUserById(Integer userId, String currencySymbol) throws SQLException;
    List<CryptoHoldingForProfileDTO> getCryptoHoldingsForUserById(Integer userId) throws SQLException;
}
