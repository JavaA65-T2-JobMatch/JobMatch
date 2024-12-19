package com.example.demo.controllers.MVC;

import com.example.demo.enums.UserRole;
import com.example.demo.models.UserEntity;
import com.example.demo.repositories.interfaces.UserRepository;
import com.example.demo.service.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final UserRepository userRepository;
    private final UserService userService;

    public HomeMvcController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping
    public String homePage(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            UserRole role = userService.findRoleByUsername(username);

            model.addAttribute("isAuthenticated", true);
            model.addAttribute("username", username);
            model.addAttribute("role", role.toString());
        } else {
            model.addAttribute("isAuthenticated", false);
            model.addAttribute("role",null);
        }

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

    @GetMapping("/profile")
    public RedirectView redirectToProfilePage(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new RedirectView("/user/login");
        }

        String role = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority().replace("ROLE_", ""))
                .findFirst()
                .orElse(null);

        if ("COMPANY".equals(role)) {
            return new RedirectView("/company/profile");
        } else if ("PROFESSIONAL".equals(role)) {
            return new RedirectView("/professional/profile");
        }

        return new RedirectView("/user/login");
    }
}
