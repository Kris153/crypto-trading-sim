package com.crypto.simulator.backend.service.impl;

import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.repository.UserRepository;
import com.crypto.simulator.backend.user.CryptoTradingUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.Optional;

public class CryptoTradingUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CryptoTradingUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try {
            Optional<User> user = userRepository.findByUsername(username);
            return user.map(CryptoTradingUserDetailsService::map)
                    .orElseThrow(
                            () -> new UsernameNotFoundException("User with username " + username + " not found!"));
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static UserDetails map(User user) {

        return new CryptoTradingUserDetails(
                user.getUsername(),
                user.getPassword()
        );
    }
}
