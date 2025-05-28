package com.crypto.simulator.backend.repository.impl;

import com.crypto.simulator.backend.model.Transaction;
import com.crypto.simulator.backend.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private final DataSource dataSource;

    public TransactionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, crypto_symbol, timestamp, is_buy, quantity, price_per_unit, total_value, balance_after) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, transaction.getUserId());
            ps.setString(2, transaction.getCryptoSymbol());
            ps.setTimestamp(3, Timestamp.valueOf(transaction.getTimestamp()));
            ps.setBoolean(4, transaction.isBuy());
            ps.setBigDecimal(5, transaction.getQuantity());
            ps.setBigDecimal(6, transaction.getPricePerUnit());
            ps.setBigDecimal(7, transaction.getTotalValue());
            ps.setBigDecimal(8, transaction.getBalanceAfter());

            ps.executeUpdate();
        }
    }
}
