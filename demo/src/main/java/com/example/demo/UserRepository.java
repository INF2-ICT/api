package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.User;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
}