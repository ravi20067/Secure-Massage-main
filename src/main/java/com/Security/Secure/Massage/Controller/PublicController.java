package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class PublicController {

    @GetMapping("/")
    public String home(Model model, Principal principal) {

        model.addAttribute("currentPage", "home");
        return "index";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("currentPage", "login");
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("currentPage", "register");
        model.addAttribute("user", new User());
        return "register";
    }
}