package com.pondmanage.security;

import com.pondmanage.model.Account;
import com.pondmanage.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountDetailsServiceImp implements UserDetailsService {
    @Autowired
    private AccountRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Account user = repo.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("NOT FOUND");
        }
        return new CustomAccountDetails(user);
    }

}
