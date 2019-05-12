package com.secure.example.auth.data.access;

import com.secure.example.auth.data.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
  boolean existsByLogin(String login);
  Optional<Account> findByLogin(String login);
}
