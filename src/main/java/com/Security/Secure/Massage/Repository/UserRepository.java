package com.Security.Secure.Massage.Repository;

import com.Security.Secure.Massage.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
