package com.security.validation;

import com.security.model.User;
import com.security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class CustomValidator {

    public ResponseEntity<Object> valid(@RequestBody User user) {
        try {

            String firstName = user.getFirstName();
            if (firstName == null || firstName.equalsIgnoreCase("")) {
                return ResponseEntity.badRequest().body("Firstname  is Required");
            }
            String lastname = user.getLastName();
            if (lastname == null || lastname.equalsIgnoreCase("")) {
                return ResponseEntity.badRequest().body("Lastname  is Required");
            }
            String email = user.getEmail();
            if (email == null || email.equalsIgnoreCase("")) {
                return ResponseEntity.badRequest().body("Email is Required");
            }
            String username = user.getUsername();
            if (username == null || username.equalsIgnoreCase("")) {
                return ResponseEntity.badRequest().body("Username is Required");
            }
            String password = user.getPassword();
            if (password == null || password.equalsIgnoreCase("")) {
                return ResponseEntity.badRequest().body("Password  is Required");
            }

//            Role role = user.getRoles();
//            if (role == null || role.getRoleName().equalsIgnoreCase("")) {
//                return ResponseEntity.badRequest().body("Role is Required");
//
//            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Validator not found");
        }
    }
}
