package com.Backend.Login.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.Backend.Login.Model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}

