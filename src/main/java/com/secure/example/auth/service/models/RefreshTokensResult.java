package com.secure.example.auth.service.models;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RefreshTokensResult {
  private String accessToken;
  private String refreshToken;
}
