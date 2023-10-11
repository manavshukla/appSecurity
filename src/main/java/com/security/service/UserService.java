package com.security.service;

import com.security.dto.EmployeeDto;
import com.security.model.User;

public interface UserService {

    User registerUser(User user, String roleName);

    String login(String username, String password);

    User getUserByUsername(String username);

    void logout(String token);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    EmployeeDto getEmployeeByUserIdAndStatus(String userId, int status);
}