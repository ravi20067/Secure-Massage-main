package com.Security.Secure.Massage.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@CompoundIndex(
        name = "admin_email_idx",
        def = "{'adminEmail': 1}"
)
public class Group {
    @Id
    private String id;

    private String name;

    private String adminEmail;

    private String groupImage;

    private LocalDateTime createdAt;

}
