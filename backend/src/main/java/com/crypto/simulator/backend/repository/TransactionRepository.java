package com.crypto.simulator.backend.repository;

import com.crypto.simulator.backend.model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction) throws SQLException;
    List<Transaction> getTransactionsForUserById(Integer userId) throws SQLException;
    void deleteTransactionsByUserId(Integer userId) throws SQLException;
}
