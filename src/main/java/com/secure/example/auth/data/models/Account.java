package com.secure.example.auth.data.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@javax.persistence.Entity
public class Account {
  @Id @GeneratedValue private Long id;
  private LocalDateTime createdTime;
  private LocalDateTime deletedTime;
  private String login;
  private String password;
  private String salt;
  private String role;
}
