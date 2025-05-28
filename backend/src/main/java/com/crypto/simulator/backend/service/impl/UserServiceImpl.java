package com.crypto.simulator.backend.service.impl;

import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.model.dtos.UserRegisterDTO;
import com.crypto.simulator.backend.repository.UserRepository;
import com.crypto.simulator.backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserRegisterDTO registerData) throws SQLException {
        User userToAdd = new User();
        userToAdd.setUsername(registerData.getUsername());
        userToAdd.setPassword(registerData.getPassword());
        userToAdd.setConfirmPassword(registerData.getConfirmPassword());
        userToAdd.setBalance(BigDecimal.valueOf(10000));
        userToAdd.setPassword(this.passwordEncoder.encode(registerData.getPassword()));
        userRepository.saveUser(userToAdd);
    }

    @Override
    public boolean confirmPassword(UserRegisterDTO registerData) {
        return registerData.getPassword().equals(registerData.getConfirmPassword());
    }

    @Override
    public boolean doUsernameExists(UserRegisterDTO registerData) throws SQLException {
        return this.userRepository.existsUserEntityByUsername(registerData.getUsername());
    }
}
