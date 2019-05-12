package com.secure.example.auth.service.models;

import lombok.Data;

@Data
public class RegisterRequest {
  private String login;
  private String password;
  // todo additional info
}
