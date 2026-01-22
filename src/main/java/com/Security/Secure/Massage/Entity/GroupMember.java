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
                name = "group_user_idx",
                def = "{'groupId': 1, 'userEmail': 1}"
        ),
        @CompoundIndex(
                name = "user_groups_idx",
                def = "{'userEmail': 1}"
        )
})
public class GroupMember {
    @Id
    private String id;

    private String groupId;

    private String userEmail;

    private String role; // ADMIN, MEMBER

    private LocalDateTime joinedAt;
}
