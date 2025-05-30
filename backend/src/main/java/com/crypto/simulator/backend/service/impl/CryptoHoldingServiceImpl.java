package com.crypto.simulator.backend.service.impl;

import com.crypto.simulator.backend.model.dtos.CryptoHoldingForProfileDTO;
import com.crypto.simulator.backend.repository.CryptoHoldingRepository;
import com.crypto.simulator.backend.service.CryptoHoldingService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class CryptoHoldingServiceImpl implements CryptoHoldingService {

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
    private final  CryptoHoldingRepository cryptoHoldingRepository;

    public CryptoHoldingServiceImpl(CryptoHoldingRepository cryptoHoldingRepository) {
        this.cryptoHoldingRepository = cryptoHoldingRepository;
    }

    @Override
    public List<CryptoHoldingForProfileDTO> getCryptoHoldingsForUserById(Integer userId) throws SQLException {
        List<CryptoHoldingForProfileDTO> cryptoHoldingsForUserById = this.cryptoHoldingRepository.getCryptoHoldingsForUserById(userId);
        cryptoHoldingsForUserById.forEach(h -> h.setName(CURRENCY_MAP.get(h.getCryptoSymbol())));
        return cryptoHoldingsForUserById;
    }
}
