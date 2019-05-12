package com.secure.example.auth.service.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
public class Tokens {
  private String accessToken;
  private String refreshToken;

  public String accessToken() {
    return accessToken;
  }

  public String refreshToken() {
    return refreshToken;
  }
}
