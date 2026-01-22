package com.Security.Secure.Massage.MessageDTO;

import lombok.Data;

@Data
public class GroupMessageDTO {
    private String groupId;
    private String senderEmail;
    private String encryptedContent;
    private String messageType;
}
