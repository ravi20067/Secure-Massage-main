package com.Security.Secure.Massage.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@CompoundIndexes({
        @CompoundIndex(
                name = "chat_idx",
                def = "{'senderEmail': 1, 'receiverEmail': 1, 'timestamp': 1}"
        )
})
public class Message {
    @Id
    private String id;

    private String senderEmail;

    private String receiverEmail;

    private String encryptedContent;

    private String messageType;

    private String status;

    private LocalDateTime timestamp;
}
