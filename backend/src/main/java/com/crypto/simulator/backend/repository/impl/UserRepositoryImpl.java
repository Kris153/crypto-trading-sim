package com.crypto.simulator.backend.repository.impl;

import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, balance, password) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setBigDecimal(2, user.getBalance());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getBigDecimal("balance"));
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public boolean existsUserEntityByUsername(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }

        return false;
    }

    @Override
    public BigDecimal getCurrencyQuantityBySymbol(String symbol, User currentUser) throws SQLException {
        String sql = "SELECT amount FROM holdings WHERE crypto_symbol = ? AND user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, symbol);
            stmt.setLong(2, currentUser.getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal("amount");
                return total != null ? total : BigDecimal.ZERO;
            } else {
                return BigDecimal.ZERO;
            }
        }
    }

    @Override
    public void updateUserBalance(Integer userId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newBalance);
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        }
    }
}