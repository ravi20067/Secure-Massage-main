package com.Security.Secure.Massage.Controller;

import com.Security.Secure.Massage.MessageDTO.GroupMessageDTO;
import com.Security.Secure.Massage.MessageDTO.PrivateMessageDTO;
import com.Security.Secure.Massage.Security.ChatService;
import com.Security.Secure.Massage.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @Autowired
    private GroupService groupService;

    // 🔹 1-to-1 private chat
    @MessageMapping("/chat.private")
    public void handlePrivateMessage(PrivateMessageDTO dto) {

        // save message
        chatService.saveMessage(
                dto.getSenderEmail(),
                dto.getReceiverEmail(),
                dto.getEncryptedContent(),
                dto.getMessageType()
        );

        // send to receiver
        messagingTemplate.convertAndSendToUser(
                dto.getReceiverEmail(),
                "/queue/messages",
                dto
        );
    }

    // 🔹 group chat
    @MessageMapping("/chat.group")
    public void handleGroupMessage(GroupMessageDTO dto) {

        groupService.saveGroupMessage(
                dto.getGroupId(),
                dto.getSenderEmail(),
                dto.getEncryptedContent(),
                dto.getMessageType()
        );

        messagingTemplate.convertAndSend(
                "/topic/group/" + dto.getGroupId(),
                dto
        );
    }
}

