package com.Security.Secure.Massage.Repository;

import com.Security.Secure.Massage.Entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    List<Group> findByAdminEmail(String adminEmail);
}
