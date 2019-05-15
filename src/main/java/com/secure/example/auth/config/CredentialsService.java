package com.secure.example.auth.config;

import com.secure.example.auth.data.access.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("userService")
public class CredentialsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public CredentialsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = accountRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));

        var authority = new SimpleGrantedAuthority(account.getRole());
        return new User(account.getLogin(), account.getPassword(), List.of(authority));
    }
}
