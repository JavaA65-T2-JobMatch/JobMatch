package com.example.demo.controllers.MVC;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("title", "Welcome to the Application!");
        model.addAttribute("message", "Explore our features and find what suits you best.");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Us");
        model.addAttribute("message", "Learn more about our mission, vision, and values.");
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact Us");
        model.addAttribute("message", "Feel free to reach out for any inquiries or support.");
        return "contact";
    }
}
