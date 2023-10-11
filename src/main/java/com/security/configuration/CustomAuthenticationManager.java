package com.security.configuration;

import com.security.model.User;
import com.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationProvider {

    private final UserRepo userRepo;

    @Autowired
    public CustomAuthenticationManager(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {

            String username = authentication.getName();
            String password = authentication.getCredentials().toString();
            User user = userRepo.findByUsername(username);
            if (user == null || !password.equals(user.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType.equals(UsernamePasswordAuthenticationToken.class);
    }
}

