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
public class SellController {
    private final CurrencyService currencyService;
    private final UserService userService;

    public SellController(CurrencyService currencyService, UserService userService) {
        this.currencyService = currencyService;
        this.userService = userService;
    }
    @ModelAttribute("isQuantityPositive")
    public boolean addIsQuantityPositiveValueToModel(){
        return true;
    }
    @ModelAttribute("hasEnoughQuantity")
    public boolean addHasEnoughQuantityValueToModel(){
        return true;
    }

    @GetMapping("/sell/{symbol}")
    public String viewBuy(@PathVariable("symbol") String currencySymbol, Model model) throws SQLException {
        model.addAttribute("currencyName", this.currencyService.getCurrencyNameBySymbol(currencySymbol));
        model.addAttribute("currencyQuantity", this.currencyService.getCurrencyQuantityBySymbol(currencySymbol, this.userService.getCurrentUser()));
        model.addAttribute("currentBalance", this.userService.getCurrentUser().getBalance());
        model.addAttribute("currencySymbol", currencySymbol);
        return "sell";
    }
    @PostMapping("/sell/{symbol}")
    public String buyCrypto(@PathVariable("symbol") String currencySymbol, @RequestParam BigDecimal currentPrice, @RequestParam BigDecimal quantity, RedirectAttributes redirectAttributes) throws SQLException {
        if(quantity.compareTo(BigDecimal.ZERO) <= 0){
            redirectAttributes.addFlashAttribute("isQuantityPositive", false);
            return "redirect:/sell/" + currencySymbol;
        }
        if(!this.userService.hasEnoughQuantity(currencySymbol, quantity)){
            redirectAttributes.addFlashAttribute("hasEnoughQuantity", false);
            return "redirect:/sell/" + currencySymbol;
        }
        this.userService.sellCrypto(currencySymbol, currentPrice, quantity);
        return "redirect:/profile";
    }
}
