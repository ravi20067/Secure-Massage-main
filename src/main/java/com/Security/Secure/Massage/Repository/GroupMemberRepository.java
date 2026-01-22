package com.Security.Secure.Massage.Repository;

import com.Security.Secure.Massage.Entity.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupMemberRepository extends MongoRepository<GroupMember, String> {

    List<GroupMember> findByUserEmail(String email);

    boolean existsByGroupIdAndUserEmail(String groupId, String email);
}
