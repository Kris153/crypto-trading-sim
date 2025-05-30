package com.crypto.simulator.backend.repository.impl;

import com.crypto.simulator.backend.model.CryptoHolding;
import com.crypto.simulator.backend.model.dtos.CryptoHoldingForProfileDTO;
import com.crypto.simulator.backend.repository.CryptoHoldingRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    holding.setId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public void updateCryptoHoldings(Integer userId, String currencySymbol, BigDecimal amount) throws SQLException {
        String select = "SELECT amount FROM holdings WHERE user_id = ? AND crypto_symbol = ?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(select)) {
            stmt.setLong(1, userId);
            stmt.setString(2, currencySymbol);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal existing = rs.getBigDecimal("amount");
                BigDecimal updated = existing.add(amount);

                if (updated.compareTo(BigDecimal.ZERO) <= 0) {
                    String delete = "DELETE FROM holdings WHERE user_id = ? AND crypto_symbol = ?";
                    try (PreparedStatement deleteStmt = connection.prepareStatement(delete)) {
                        deleteStmt.setLong(1, userId);
                        deleteStmt.setString(2, currencySymbol);
                        deleteStmt.executeUpdate();
                    }
                } else {
                    String update = "UPDATE holdings SET amount = ? WHERE user_id = ? AND crypto_symbol = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(update)) {
                        updateStmt.setBigDecimal(1, updated);
                        updateStmt.setLong(2, userId);
                        updateStmt.setString(3, currencySymbol);
                        updateStmt.executeUpdate();
                    }
                }
            } else {
                String insert = "INSERT INTO holdings (user_id, crypto_symbol, amount) VALUES (?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insert)) {
                    insertStmt.setLong(1, userId);
                    insertStmt.setString(2, currencySymbol);
                    insertStmt.setBigDecimal(3, amount);
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    @Override
    public BigDecimal getAmountOfCryptoForUserById(Integer userId, String currencySymbol) throws SQLException {
        String sql = "SELECT amount FROM holdings WHERE user_id = ? AND crypto_symbol = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setString(2, currencySymbol);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    BigDecimal ownedAmount = rs.getBigDecimal("amount");
                    return ownedAmount;
                } else {
                    return null;
                }
            }
        }
    }

    @Override
    public List<CryptoHoldingForProfileDTO> getCryptoHoldingsForUserById(Integer userId) throws SQLException {
        List<CryptoHoldingForProfileDTO> holdings = new ArrayList<>();

        String sql = "SELECT crypto_symbol, amount FROM holdings WHERE user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String symbol = rs.getString("crypto_symbol");
                BigDecimal amount = rs.getBigDecimal("amount");
                holdings.add(new CryptoHoldingForProfileDTO(userId, symbol, amount));
            }
        }

        return holdings;
    }

    @Override
    public void deleteHoldingsByUserId(Integer userId) throws SQLException {

        String deleteHoldingsSql = "DELETE FROM holdings WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteHoldingsStmt = connection.prepareStatement(deleteHoldingsSql)) {
            deleteHoldingsStmt.setLong(1, userId);
            deleteHoldingsStmt.executeUpdate();
        }
    }
}
