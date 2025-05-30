package com.crypto.simulator.backend.web;

import com.crypto.simulator.backend.service.CryptoHoldingService;
import com.crypto.simulator.backend.service.TransactionService;
import com.crypto.simulator.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.SQLException;

@Controller
public class ProfileController {
    private final CryptoHoldingService cryptoHoldingService;
    private final UserService userService;
    private final TransactionService transactionService;

    public ProfileController(CryptoHoldingService cryptoHoldingService, UserService userService, TransactionService transactionService) {
        this.cryptoHoldingService = cryptoHoldingService;
        this.userService = userService;
        this.transactionService = transactionService;
    }
    @ModelAttribute("successfulReset")
    private boolean addSuccessfulResetValueToModel(){return false;}
    @GetMapping("/profile")
    public String viewProfilePage(Model model) throws SQLException {
        model.addAttribute("cryptoHoldingsList", this.cryptoHoldingService.getCryptoHoldingsForUserById(this.userService.getCurrentUser().getId()));
        if(this.userService.getCurrentUser() == null){
            model.addAttribute("currentBalance", BigDecimal.valueOf(0));
        }else{
            model.addAttribute("currentBalance", this.userService.getCurrentUser().getBalance());
        }
        model.addAttribute("transactionsList", this.transactionService.getTransactionsForUserById(this.userService.getCurrentUser().getId()));
        return "profile";
    }
    @PostMapping("/reset_balance")
    public String resetBalance(RedirectAttributes redirectAttributes) throws SQLException {
        if(this.userService.resetBalance()){
         redirectAttributes.addFlashAttribute("successfulReset", true);
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }
}
