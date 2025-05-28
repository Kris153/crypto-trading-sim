package com.crypto.simulator.backend.repository;

import com.crypto.simulator.backend.model.User;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    void saveUser(User user) throws SQLException;
    Optional<User> findByUsername(String username) throws SQLException;

    boolean existsUserEntityByUsername(String username) throws SQLException;
}
