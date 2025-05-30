package com.crypto.simulator.backend.service.impl;

import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.repository.UserRepository;
import com.crypto.simulator.backend.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {
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
    private final UserRepository userRepository;

    public CurrencyServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getCurrencyNameBySymbol(String symbol) {
        return CURRENCY_MAP.get(symbol);
    }

    @Override
    public BigDecimal getCurrencyQuantityBySymbol(String symbol, User currentUser) throws SQLException {
        return userRepository.getCurrencyQuantityBySymbol(symbol, currentUser);
    }
}
