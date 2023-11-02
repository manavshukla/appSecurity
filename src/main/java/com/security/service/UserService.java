package com.security.service;

import com.security.dto.EmployeeDto;
import com.security.model.User;

import java.util.List;

public interface UserService {

    User registerUser(User user, String roleName);

    String login(String username, String password, String userId);

    User getUserByUsername(String username);

    void logout(String token);

    List<User> getUsersWithIdAndName();

    EmployeeDto getEmployeeByUserIdAndStatus(String userId, int status);
}