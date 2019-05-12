package com.secure.example.auth.exceptions;

public class LoginAlreadyExistsException extends RegistrationException {
  public LoginAlreadyExistsException(String message) {
    super(message);
  }
}
