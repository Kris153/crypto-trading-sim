package com.crypto.simulator.backend.repository.impl;

import com.crypto.simulator.backend.model.CryptoHolding;
import com.crypto.simulator.backend.repository.CryptoHoldingRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class CryptoHoldingRepositoryImpl implements CryptoHoldingRepository {
    private final DataSource dataSource;

    public CryptoHoldingRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void saveCryptoHolding(CryptoHolding holding) throws SQLException {
        String sql = "INSERT INTO crypto_holdings (user_id, crypto_symbol, quantity) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, holding.getUserId());
            ps.setString(2, holding.getCryptoSymbol());
            ps.setBigDecimal(3, holding.getQuantity());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    holding.setId(keys.getLong(1));
                }
            }
        }
    }
}
