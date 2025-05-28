package com.crypto.simulator.backend.service;

import com.crypto.simulator.backend.model.dtos.UserRegisterDTO;

import java.sql.SQLException;

public interface UserService {
    void register(UserRegisterDTO registerData) throws SQLException;

    boolean confirmPassword(UserRegisterDTO registerData);

    boolean doUsernameExists(UserRegisterDTO registerData) throws SQLException;
}
