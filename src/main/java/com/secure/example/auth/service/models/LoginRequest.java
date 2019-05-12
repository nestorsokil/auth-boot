package com.secure.example.auth.service.models;

import lombok.Data;

@Data
public class LoginRequest {
  private String login;
  private String password;
}
