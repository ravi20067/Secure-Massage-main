package com.Security.Secure.Massage.Repository;

import com.Security.Secure.Massage.Entity.GroupMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupMessageRepository extends MongoRepository<GroupMessage, String> {
    List<GroupMessage> findByGroupIdOrderByTimestampAsc(String groupId);
}
