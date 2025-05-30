package com.crypto.simulator.backend.service;

import com.crypto.simulator.backend.model.dtos.CryptoHoldingForProfileDTO;

import java.sql.SQLException;
import java.util.List;

public interface CryptoHoldingService {
    List<CryptoHoldingForProfileDTO> getCryptoHoldingsForUserById(Integer userId) throws SQLException;
}
