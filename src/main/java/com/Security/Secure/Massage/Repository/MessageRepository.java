package com.Security.Secure.Massage.Repository;

import com.Security.Secure.Massage.Entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderEmailAndReceiverEmailOrReceiverEmailAndSenderEmailOrderByTimestampAsc(
            String sender1,
            String receiver1,
            String sender2,
            String receiver2
    );
}
