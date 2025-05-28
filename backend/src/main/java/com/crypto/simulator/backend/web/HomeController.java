package com.crypto.simulator.backend.web;

import com.crypto.simulator.backend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @ModelAttribute("hasErrors")
    private boolean addHasErrorsValueToModel(){return false;}
    @ModelAttribute("loginData")
    private User addUserLoginDTOToModel(){
        return new User();
    }
    @GetMapping("/")
    public String viewHomePage(Model model){
        return "index";
    }
    @GetMapping("/login")
    public String viewLoginPage(Model model){
        return "login";
    }
    @GetMapping("/profile")
    public String viewProfilePage(Model model){
        return "profile";
    }
    @GetMapping("/buy")
    public String viewBuyPage(Model model){
        return "buy";
    }
    @GetMapping("/sell")
    public String viewSellPage(Model model){
        return "sell";
    }
}
