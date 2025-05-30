package com.crypto.simulator.backend.service.impl;

import com.crypto.simulator.backend.model.Transaction;
import com.crypto.simulator.backend.repository.TransactionRepository;
import com.crypto.simulator.backend.service.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {
    public static final Map<String, String> CURRENCY_MAP = Map.ofEntries(
            Map.entry("BTC", "Bitcoin"),
            Map.entry("ETH", "Ethereum"),
            Map.entry("USDT", "Tether"),
            Map.entry("USDC", "USD Coin"),
            Map.entry("BNB", "BNB"),
            Map.entry("XRP", "XRP"),
            Map.entry("ADA", "Cardano"),
            Map.entry("SOL", "Solana"),
            Map.entry("DOGE", "Dogecoin"),
            Map.entry("DOT", "Polkadot"),
            Map.entry("DAI", "Dai"),
            Map.entry("TRX", "TRON"),
            Map.entry("AVAX", "Avalanche"),
            Map.entry("LTC", "Litecoin"),
            Map.entry("XLM", "Stellar"),
            Map.entry("BCH", "Bitcoin Cash"),
            Map.entry("LINK", "Chainlink"),
            Map.entry("UNI", "Uniswap"),
            Map.entry("ALGO", "Algorand"),
            Map.entry("ATOM", "Cosmos")
    );
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactionsForUserById(Integer userId) throws SQLException {
        List<Transaction> transactionsForUserById = transactionRepository.getTransactionsForUserById(userId);
        transactionsForUserById.forEach(t -> t.setCryptoName(CURRENCY_MAP.get(t.getCryptoSymbol())));
        return transactionsForUserById;
    }
}
