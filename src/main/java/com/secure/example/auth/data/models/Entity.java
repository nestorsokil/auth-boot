package com.secure.example.auth.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
abstract class Entity {
  @Id
  private String id;
  private LocalDateTime createdTime;
  private LocalDateTime deletedTime;
}
