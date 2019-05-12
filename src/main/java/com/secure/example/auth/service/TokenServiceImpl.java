package com.secure.example.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.secure.example.auth.data.models.Account;
import com.secure.example.auth.service.models.Tokens;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
  @Override
  public Tokens createTokens(Account identity) {
    var refreshToken = UUID.randomUUID().toString();
    var accessToken = JWT.create()
        .withClaim("identity", identity.getLogin())
        .withExpiresAt(newExpiration())
        .sign(getAlgorithm());
    return Tokens.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  private static Date newExpiration() {
    Calendar calender = Calendar.getInstance();
    calender.add(Calendar.MINUTE, 30);
    return calender.getTime();
  }

  private static Algorithm getAlgorithm() {
    return Algorithm.HMAC256("secret");
  }
}
