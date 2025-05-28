package com.crypto.simulator.backend.repository;

import com.crypto.simulator.backend.model.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction) throws SQLException;
}
