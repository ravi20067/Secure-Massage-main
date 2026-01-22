package com.Security.Secure.Massage.Entity;

import com.Security.Secure.Massage.Enums.AuthType;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;
    private String name;

    public User() {

    }

    @Indexed(unique = true)
    @NonNull
    private String email;
    private String password;
    @NonNull
    private AuthType authType;
    private String profileImage;
    private boolean online;
    private List<String> roles = new ArrayList<>();


}
