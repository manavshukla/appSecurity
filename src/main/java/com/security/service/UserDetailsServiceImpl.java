package com.security.service;

import com.security.model.User;
import com.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            User user = userRepo.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("user not found");
            }
            List<GrantedAuthority> authorityList = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorityList);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
