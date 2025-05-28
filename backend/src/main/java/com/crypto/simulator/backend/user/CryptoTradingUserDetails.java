package com.crypto.simulator.backend.user;

import org.springframework.security.core.userdetails.User;

import java.util.Collections;


public class CryptoTradingUserDetails extends User {
    public CryptoTradingUserDetails(String username, String password) {
        super(username, password, Collections.emptyList());
    }
}
