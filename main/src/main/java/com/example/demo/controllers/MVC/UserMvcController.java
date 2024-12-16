package com.example.demo.controllers.MVC;


import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserMvcController {

    @GetMapping("/login")
    public String showLoginPage(){
        return "userCommands/login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(){
        return "userCommands/registration";
    }

    @GetMapping("/home")
    public String showHomePage(Model model){
        return "home";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/users/login";
    }
}
