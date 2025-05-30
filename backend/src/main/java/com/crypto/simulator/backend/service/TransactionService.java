package com.crypto.simulator.backend.service;

import com.crypto.simulator.backend.model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionsForUserById(Integer userId) throws SQLException;
}
