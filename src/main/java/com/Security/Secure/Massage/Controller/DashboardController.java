package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.User;
import com.Security.Secure.Massage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private UserService userService;

    /* ===============================
       DASHBOARD HOME
    ================================ */

    @GetMapping("/home")
    public String dashboard(Model model, Authentication authentication) {

        model.addAttribute("activePage", "dashboard");

        User user = userService.findByEmail(authentication.getName())
                        .orElseThrow(() -> new RuntimeException("User not Found"));

        model.addAttribute("user",user);

        // Dummy stats (replace with service calls later)
        model.addAttribute("totalMessages", 12);
        model.addAttribute("unreadMessages", 3);
        model.addAttribute("groupsCount", 4);
        model.addAttribute("filesCount", 7);

        return "dashboard";
    }

    /* ===============================
       MESSAGES
    ================================ */

    @GetMapping("/messages")
    public String messages(Model model, Principal principal) {
        System.out.println(principal.getName());
        model.addAttribute("activePage", "messages");
        return "messages";
    }

    /* ===============================
       GROUPS
    ================================ */

    @GetMapping("/groups")
    public String groups(Model model) {
        model.addAttribute("activePage", "groups");
        return "groups";
    }

    /* ===============================
       PRIVATE CHATS
    ================================ */

    @GetMapping("/chats")
    public String chats(Model model) {
        model.addAttribute("activePage", "chats");
        return "chats";
    }

    /* ===============================
       UPLOADS
    ================================ */

    @GetMapping("/uploads")
    public String uploads(Model model) {
        model.addAttribute("activePage", "uploads");
        return "uploads";
    }

    /* ===============================
       PROFILE
    ================================ */

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {

        model.addAttribute("activePage", "profile");

        User user = userService.getCurrentUser(authentication);

        model.addAttribute("user", user);

        return "profile";
    }

    /* ===============================
       SETTINGS
    ================================ */

    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("activePage", "settings");
        return "settings";
    }
}
