package com.Security.Secure.Massage.Security;

import com.Security.Secure.Massage.Entity.Message;
import com.Security.Secure.Massage.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(
            String senderEmail,
            String receiverEmail,
            String encryptedContent,
            String messageType
    ) {

        Message message = new Message();
        message.setSenderEmail(senderEmail);
        message.setReceiverEmail(receiverEmail);
        message.setEncryptedContent(encryptedContent);
        message.setMessageType(messageType);
        message.setStatus("SENT");
        message.setTimestamp(LocalDateTime.now());

        return messageRepository.save(message);
    }
    public List<Message> getChatHistory(String user1, String user2) {

        return messageRepository
                .findBySenderEmailAndReceiverEmailOrReceiverEmailAndSenderEmailOrderByTimestampAsc(
                        user1, user2,
                        user1, user2
                );
    }
    public void updateStatus(String messageId, String status) {
        messageRepository.findById(messageId).ifPresent(msg -> {
            msg.setStatus(status);
            messageRepository.save(msg);
        });
    }
}
