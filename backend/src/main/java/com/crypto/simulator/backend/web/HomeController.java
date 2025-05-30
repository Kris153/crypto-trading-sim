package com.crypto.simulator.backend.web;

import com.crypto.simulator.backend.model.User;
import com.crypto.simulator.backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.sql.SQLException;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("hasErrors")
    private boolean addHasErrorsValueToModel(){return false;}
    @ModelAttribute("loginData")
    private User addUserLoginDTOToModel(){
        return new User();
    }
    @GetMapping("/")
    public String viewHomePage(Model model) throws SQLException {
        if(this.userService.getCurrentUser() == null){
            model.addAttribute("currentBalance", BigDecimal.valueOf(0));
        }else{
            model.addAttribute("currentBalance", this.userService.getCurrentUser().getBalance());
        }
        return "index";
    }
    @GetMapping("/login")
    public String viewLoginPage(Model model){
        return "login";
    }
}
