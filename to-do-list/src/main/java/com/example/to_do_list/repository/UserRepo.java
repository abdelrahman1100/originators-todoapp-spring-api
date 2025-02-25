package com.example.to_do_list.repository;


import com.example.to_do_list.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}
