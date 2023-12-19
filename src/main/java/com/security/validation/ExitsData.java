package com.security.validation;

import com.security.model.User;
import com.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ExitsData {
    private final UserRepo userRepo;

    @Autowired
    public ExitsData(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<Object> exists(@RequestBody User user) {
        try {

            if (userRepo.existsByEmail(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            if (userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
                return ResponseEntity.badRequest().body("Phonenumber already exists");
            }

            if (userRepo.existsByUsername(user.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Exists data");
        }
    }
}
