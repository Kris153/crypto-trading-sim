package com.crypto.simulator.backend.web;

import com.crypto.simulator.backend.service.CurrencyService;
import com.crypto.simulator.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.SQLException;

@Controller
public class BuyController {
    private final CurrencyService currencyService;
    private final UserService userService;

    public BuyController(CurrencyService currencyService, UserService userService) {
        this.currencyService = currencyService;
        this.userService = userService;
    }
    @ModelAttribute("hasEnoughMoney")
    public boolean addHasEnoughMoneyValueToModel(){
        return true;
    }

    @ModelAttribute("isQuantityPositive")
    public boolean addisQuantityPositiveValueToModel(){
        return true;
    }

    @GetMapping("/buy/{symbol}")
    public String viewBuy(@PathVariable("symbol") String currencySymbol, Model model) throws SQLException {
        model.addAttribute("currencyName", this.currencyService.getCurrencyNameBySymbol(currencySymbol));
        model.addAttribute("currencyQuantity", this.currencyService.getCurrencyQuantityBySymbol(currencySymbol, this.userService.getCurrentUser()));
        model.addAttribute("currentBalance", this.userService.getCurrentUser().getBalance());
        model.addAttribute("currencySymbol", currencySymbol);
        return "buy";
    }

    @PostMapping("/buy/{symbol}")
    public String buyCrypto(@PathVariable("symbol") String currencySymbol, @RequestParam BigDecimal currentPrice, @RequestParam BigDecimal quantity, RedirectAttributes redirectAttributes) throws SQLException {
        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            redirectAttributes.addFlashAttribute("isQuantityPositive", false);
            return "redirect:/buy/" + currencySymbol;
        }
        if(!this.userService.hasEnoughMoneyToBuy(currencySymbol, currentPrice, quantity)){
            redirectAttributes.addFlashAttribute("hasEnoughMoney", false);
            return "redirect:/buy/" + currencySymbol;
        }
        this.userService.buyCrypto(currencySymbol, currentPrice, quantity);
        return "redirect:/profile";
    }
}
