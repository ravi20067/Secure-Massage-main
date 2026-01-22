package com.Security.Secure.Massage.MessageDTO;

import lombok.Data;

@Data
public class PrivateMessageDTO {

    private String senderEmail;
    private String receiverEmail;
    private String encryptedContent;
    private String messageType;
}

