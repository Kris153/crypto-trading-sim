package com.crypto.simulator.backend.repository;

import com.crypto.simulator.backend.model.CryptoHolding;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface CryptoHoldingRepository {
    void saveCryptoHolding(CryptoHolding holding) throws SQLException;
}
