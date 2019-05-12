package com.secure.example.auth.data.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@Document
public class Account extends Entity {
  private String login;
  private String password;
  private String salt;
  private List<RefreshToken> refreshTokens;

  public Optional<RefreshToken> findToken(String tokenValue) {
    return getRefreshTokens().stream()
        .filter(token -> token.getToken().equals(tokenValue))
        .findFirst();
  }

  private void removeExpiredTokens() {
    setRefreshTokens(
        getRefreshTokens().stream()
            .filter(token -> !token.isExpired())
            .collect(Collectors.toList()));
  }

  public void refresh(RefreshToken refreshToken) {
    if (getRefreshTokens() != null) {
      removeExpiredTokens();
      getRefreshTokens().add(refreshToken);
    } else {
      setRefreshTokens(List.of(refreshToken));
    }
  }
}
