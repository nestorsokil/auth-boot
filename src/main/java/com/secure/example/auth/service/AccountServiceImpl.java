package com.secure.example.auth.service;

import com.secure.example.auth.data.access.AccountRepository;
import com.secure.example.auth.data.models.Account;
import com.secure.example.auth.data.models.RefreshToken;
import com.secure.example.auth.exceptions.*;
import com.secure.example.auth.service.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
  private final PasswordEncoder encoder;
  private final AccountRepository accountRepository;
  private final SaltCreator saltCreator;
  private final TokenService tokenService;

  @Autowired
  public AccountServiceImpl(
      PasswordEncoder encoder,
      AccountRepository accountRepository,
      SaltCreator saltCreator,
      TokenService tokenService
  ) {
    this.encoder = encoder;
    this.accountRepository = accountRepository;
    this.saltCreator = saltCreator;
    this.tokenService = tokenService;
  }

  @Override
  public LoginResult login(LoginRequest request) throws AccountNotFoundException, InvalidPasswordException {
    var account = accountRepository.findByLogin(request.getLogin()).orElseThrow(AccountNotFoundException::new);
    var expected = account.getPassword();
    var actual = saltPassword(request.getPassword(), account.getSalt());
    if (!actual.equals(expected)) {
      throw new InvalidPasswordException();
    }
    var tokens = tokenService.createTokens(account);
    account.refresh(RefreshToken.builder()
        .token(tokens.refreshToken())
        .build());
    accountRepository.save(account);
    return LoginResult.builder()
        .refreshToken(tokens.refreshToken())
        .accessToken(tokens.accessToken())
        .build();
  }

  @Override
  public RegisterResult register(RegisterRequest request) throws RegistrationException {
    if (accountRepository.existsByLogin(request.getLogin())) {
      throw new LoginAlreadyExistsException(String.format("%s already exists", request.getLogin()));
    }

    var account = new Account();
    var salt = saltCreator.randomSalt();
    var saltedPassword = saltPassword(request.getPassword(), salt);
    var hashedPassword = encoder.encode(saltedPassword);

    account.setLogin(request.getLogin());
    account.setSalt(salt);
    account.setPassword(hashedPassword);

    var tokens = tokenService.createTokens(account);
    account.refresh(RefreshToken.builder()
        .token(tokens.refreshToken())
        .build());
    accountRepository.save(account);
    return RegisterResult.builder()
        .refreshToken(tokens.refreshToken())
        .accessToken(tokens.accessToken())
        .accountId(account.getId())
        .build();
  }

  private static String saltPassword(String password, String salt) {
    return String.format("%s%s", password, salt);
  }

  @Override
  public RefreshTokensResult refreshTokens(RefreshTokensRequest request) throws TokenExpiredException, TokenNotFoundException, AccountNotFoundException {
    var account = accountRepository.findByLogin(request.getIdentity()).orElseThrow(AccountNotFoundException::new);
    var refreshToken = account.findToken(request.getRefreshToken()).orElseThrow(TokenNotFoundException::new);
    if (refreshToken.isExpired()) {
      throw new TokenExpiredException();
    }
    var tokens = tokenService.createTokens(account);
    account.refresh(RefreshToken.builder()
        .token(tokens.refreshToken())
        .build());
    accountRepository.save(account);
    return RefreshTokensResult.builder()
        .refreshToken(tokens.refreshToken())
        .accessToken(tokens.accessToken())
        .build();
  }
}
