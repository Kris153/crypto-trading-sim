package com.crypto.simulator.backend.service.impl;

import com.crypto.simulator.backend.model.Transaction;
import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.model.dtos.UserRegisterDTO;
import com.crypto.simulator.backend.repository.CryptoHoldingRepository;
import com.crypto.simulator.backend.repository.TransactionRepository;
import com.crypto.simulator.backend.repository.UserRepository;
import com.crypto.simulator.backend.service.UserService;
import com.crypto.simulator.backend.service.exception.ObjectNotFoundException;
import com.crypto.simulator.backend.user.CryptoTradingUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TransactionRepository transactionRepository;
    private final CryptoHoldingRepository cryptoHoldingRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TransactionRepository transactionRepository, CryptoHoldingRepository cryptoHoldingRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.transactionRepository = transactionRepository;
        this.cryptoHoldingRepository = cryptoHoldingRepository;
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

    @Override
    public User getCurrentUser() throws SQLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null &&
                authentication.getPrincipal() instanceof CryptoTradingUserDetails cryptoTradingUserDetails) {
            Optional<User> byUsername = this.userRepository.findByUsername(cryptoTradingUserDetails.getUsername());
            if(byUsername.isEmpty()){
                throw new ObjectNotFoundException();
            }
            return byUsername.get();
        }
        return null;
    }

    @Override
    public boolean hasEnoughMoneyToBuy(String currencySymbol, BigDecimal currentPrice, BigDecimal quantity) throws SQLException {
        User currentUser = getCurrentUser();
        if (currentUser == null || currentPrice == null || quantity == null) {
            return false;
        }
        BigDecimal totalCost = currentPrice.multiply(quantity);
        return currentUser.getBalance().compareTo(totalCost) >= 0;
    }

    @Override
    public boolean buyCrypto(String currencySymbol, BigDecimal currentPrice, BigDecimal quantity) throws SQLException {
        try{
            User currentUser = getCurrentUser();
            BigDecimal totalCost = currentPrice.multiply(quantity);
            BigDecimal newBalance = currentUser.getBalance().subtract(totalCost);
            currentUser.setBalance(newBalance);

            this.userRepository.updateUserBalance(currentUser.getId(), newBalance);
            this.cryptoHoldingRepository.updateCryptoHoldings(currentUser.getId(), currencySymbol, quantity);
            this.transactionRepository.saveTransaction(new Transaction(currentUser.getId(), currencySymbol, LocalDateTime.now(), true, quantity, currentPrice, totalCost, newBalance));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean hasEnoughQuantity(String currencySymbol, BigDecimal quantity) throws SQLException {
        User currentUser = getCurrentUser();
        String sql = "SELECT amount FROM holdings WHERE user_id = ? AND crypto_symbol = ?";
        BigDecimal currentAmount = cryptoHoldingRepository.getAmountOfCryptoForUserById(currentUser.getId(), currencySymbol);
        if(currentAmount == null){
            return false;
        }
        return currentAmount.compareTo(quantity) >= 0;
    }

    @Override
    public boolean sellCrypto(String currencySymbol, BigDecimal currentPrice, BigDecimal quantity) throws SQLException {
        try{
            User currentUser = getCurrentUser();

            BigDecimal totalCost = currentPrice.multiply(quantity);
            BigDecimal newBalance = currentUser.getBalance().add(totalCost);

            this.userRepository.updateUserBalance(currentUser.getId(), newBalance);

            this.cryptoHoldingRepository.updateCryptoHoldings(currentUser.getId(), currencySymbol, quantity.negate());

            this.transactionRepository.saveTransaction(new Transaction(currentUser.getId(), currencySymbol, LocalDateTime.now(), false, quantity, currentPrice, totalCost, newBalance));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean resetBalance() throws SQLException {
        try{
            User currentUser = getCurrentUser();
            this.userRepository.updateUserBalance(currentUser.getId(), BigDecimal.valueOf(10000));
            this.cryptoHoldingRepository.deleteHoldingsByUserId(currentUser.getId());
            this.transactionRepository.deleteTransactionsByUserId(currentUser.getId());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}