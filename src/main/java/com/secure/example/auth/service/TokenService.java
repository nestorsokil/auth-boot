package com.secure.example.auth.service;

import com.secure.example.auth.data.models.Account;
import com.secure.example.auth.service.models.Tokens;
import org.springframework.data.util.Pair;

public interface TokenService {
  Tokens createTokens(Account identity);
}
