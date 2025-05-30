package com.crypto.simulator.backend.repository.impl;

import com.crypto.simulator.backend.model.Transaction;
import com.crypto.simulator.backend.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private final DataSource dataSource;

    public TransactionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, crypto_symbol, transaction_time, is_buy, quantity, price_per_unit, total_value, balance_after) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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

    @Override
    public List<Transaction> getTransactionsForUserById(Integer userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT id, user_id, crypto_symbol, transaction_time, is_buy, quantity, price_per_unit, total_value, balance_after " +
                "FROM transactions WHERE user_id = ? ORDER BY transaction_time DESC";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction tx = new Transaction();
                tx.setId(rs.getInt("id"));
                tx.setUserId(rs.getInt("user_id"));
                tx.setCryptoSymbol(rs.getString("crypto_symbol"));
                tx.setTimestamp(rs.getTimestamp("transaction_time").toLocalDateTime());
                tx.setBuy(rs.getBoolean("is_buy"));
                tx.setQuantity(rs.getBigDecimal("quantity"));
                tx.setPricePerUnit(rs.getBigDecimal("price_per_unit"));
                tx.setTotalValue(rs.getBigDecimal("total_value"));
                tx.setBalanceAfter(rs.getBigDecimal("balance_after"));

                transactions.add(tx);
            }
        }

        return transactions;
    }
}
