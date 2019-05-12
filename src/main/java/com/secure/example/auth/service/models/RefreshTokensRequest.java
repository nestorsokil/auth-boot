package com.secure.example.auth.service.models;

import lombok.Data;

@Data
public class RefreshTokensRequest {
  private String identity;
  private String refreshToken;
}
