package com.Security.Secure.Massage.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@CompoundIndex(
        name = "group_chat_idx",
        def = "{'groupId': 1, 'timestamp': 1}"
)
public class GroupMessage {
    @Id
    private String id;

    private String groupId;

    private String senderEmail;

    private String encryptedContent;

    private String messageType;

    private LocalDateTime timestamp;
}
