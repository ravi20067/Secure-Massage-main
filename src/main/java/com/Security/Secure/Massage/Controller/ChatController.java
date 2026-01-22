package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.Entity.Message;
import com.Security.Secure.Massage.Security.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/{email}")
    public List<Message> getChatHistory(
            @PathVariable String email,
            Authentication authentication
    ) {

        String loggedInEmail = authentication.getName();

        return chatService.getChatHistory(loggedInEmail, email);
    }
}

