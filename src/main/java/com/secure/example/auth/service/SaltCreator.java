package com.secure.example.auth.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SaltCreator {
  private final Random random = new SecureRandom();

  public String randomSalt() {
    byte[] salt = new byte[32];
    random.nextBytes(salt);
    return new String(salt);
  }
}
