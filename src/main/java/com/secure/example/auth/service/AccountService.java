package com.secure.example.auth.service;

import com.secure.example.auth.exceptions.*;
import com.secure.example.auth.service.models.*;

public interface AccountService {
  LoginResult login(LoginRequest request) throws AccountNotFoundException, InvalidPasswordException;
  RegisterResult register(RegisterRequest request) throws RegistrationException;
  RefreshTokensResult refreshTokens(RefreshTokensRequest request) throws TokenExpiredException, TokenNotFoundException, AccountNotFoundException;
}
