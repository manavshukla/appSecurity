package com.security.controller;


import com.security.dto.AccessDto;
import com.security.dto.EmployeeDto;
import com.security.microService.EmployeeService;
import com.security.model.*;
import com.security.pojo.Employee;
import com.security.repo.JwtTokenRepo;
import com.security.repo.RoleRepo;
import com.security.repo.UserRepo;
import com.security.service.UserDetailsServiceImpl;
import com.security.service.UserServiceImpl;
import com.security.validation.CustomValidator;
import com.security.validation.ExitsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class LoginController {

    private final JwtTokenRepo jwtTokenRepo;
    private final UserRepo userRepo;
    private final EmployeeService employeeService;
    private final UserServiceImpl userService;
    private final RoleRepo roleRepo;

    private final CustomValidator validator;
    private final ExitsData exitsData;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginController(UserServiceImpl userService, UserDetailsServiceImpl userDetailsService, JwtTokenProvider jwtTokenProvider, JwtTokenRepo jwtTokenRepo, UserRepo userRepo, JwtTokenProvider jwtTokenProvider1, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService1, EmployeeService employeeService, RoleRepo roleRepo, CustomValidator validator, ExitsData exitsData) {
        this.userService = userService;
        this.jwtTokenRepo = jwtTokenRepo;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.roleRepo = roleRepo;
        this.validator = validator;
        this.exitsData = exitsData;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        try {
            int status = 0;
//            Optional<User> userRepoById = userRepo.findById(userId);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (authenticate(authRequest.getUsername(), authRequest.getPassword())) {
                AccessDto accessDto = new AccessDto();
                User user = userService.getUserByUsername(authRequest.getUsername());
                String userId = user.getId();
                if (user.getRoles().getRoleName().equals("EMPLOYEE")) {
                    EmployeeDto statusUser = employeeService.getEmployeeByUserId(user.getId());
                    if (statusUser.getStatus() == 1) {
                        String token = userService.login(authRequest.getUsername(), authRequest.getPassword(), userId);
                        JwtToken token1 = new JwtToken();
                        token1.setStartDate(LocalDateTime.now());
                        token1.setStatus(1);
                        token1.setToken(token);
                        jwtTokenRepo.save(token1);
                        accessDto.setRoleId(user.getRoleId());
                        accessDto.setToken(token);
                        accessDto.setUserId(user.getId());
                        accessDto.setRoleName(user.getRoles().getRoleName());
                        accessDto.setEmployeeName(user.getName());
                        accessDto.setName(user.getName());
                        return ResponseEntity.ok(accessDto);
                    }
                }
                String token = userService.login(authRequest.getUsername(), authRequest.getPassword(), userId);
                JwtToken token1 = new JwtToken();
                token1.setStartDate(LocalDateTime.now());
                token1.setStatus(1);
                token1.setToken(token);
                jwtTokenRepo.save(token1);
                accessDto.setRoleId(user.getRoleId());
                accessDto.setToken(token);
                accessDto.setUserId(user.getId());
                accessDto.setRoleName(user.getRoles().getRoleName());
                accessDto.setEmployeeName(user.getUsername());
                accessDto.setName(user.getName());
                return ResponseEntity.ok(accessDto);
            } else {
                return ResponseEntity.badRequest().body("User is not Active");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ResponseEntity.status(401).body("Authentication failed");
    }


    private boolean authenticate(String username, String password) {
        try {

            User user = userRepo.findByUsername(username);
            if (user == null) {
                ResponseEntity.status(401).body("user not found");
            }
            assert user != null;
            if (!user.getPassword().equals(password)) {
                ResponseEntity.badRequest().body("invelad password");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    @PutMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) throws
            IOException, ServletException, IllegalAccessException {
        try {
            String authorization = request.getHeader("Authorization");
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, authorization);
            String token = getTokenFromRequest(request);
            if (token != null) {
                JwtToken token1 = jwtTokenRepo.findByToken(token);
                token1.setEndDate(LocalDateTime.now());
                token1.setStatus(0);
                jwtTokenRepo.save(token1);
//                HttpSession session = req.getSession(false);
//                if (session != null) {
//                    session.invalidate();
//                }
                return ResponseEntity.ok("\"Logout Sucess\"");
            } else {
                return ResponseEntity.badRequest().body("\"Token not found\"");
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("\"Logout failed\"");
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) throws IllegalAccessException {
        try {

            String token = request.getHeader("Authorization");
            if (token != null && !token.equalsIgnoreCase("")) {
                return token;
            }
            throw new IllegalAccessException("Invalid Authorization header format");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody User user, HttpServletRequest request) {
        try {

            ResponseEntity<?> valid = validator.valid(user);
            ResponseEntity<?> exists = exitsData.exists(user);
            if (valid != null) {
                return ResponseEntity.badRequest().body(Objects.requireNonNull(valid.getBody()));
            }
            if (exists != null) {
                return ResponseEntity.badRequest().body(Objects.requireNonNull(exists.getBody()));
            }

//            Role role = user.getRoles();
            String roleId = user.getRoleId();
            userService.registerUser(user, roleId);
            Optional<Role> roleDetails = roleRepo.findById(roleId);
//            EmployeeDto employeeDto=new EmployeeDto();
//            employeeDt
            if (roleDetails.get().getRoleName().equals("EMPLOYEE")) {
                Employee employee = new Employee();
                employee.setUserId(user.getId());
                employee.setPhoneNo(user.getPhoneNumber());
                employee.setName(user.getName());
                employee.setEmailAddress(user.getEmail());
                employee.setRole(roleId);
                employee.setStatus(user.getStatus());
                System.out.println(employee);
                employeeService.createRegistartionEmployee(employee);
            }

//            } else {
//                EmployeeDto employeeDto = new EmployeeDto();
//                employeeDto.setUserId(user.getId());
//                employeeDto.setName((user.getUsername()));
//                employeeDto.setEmailAddress(user.getEmail());
//                employeeDto.setRole(roleId);
//                employeeDto.setStatus(user.getStatus());
//                employeeService.createEmployee(employeeDto, request, null, null);
//            }
            // }

            return ResponseEntity.ok("\"User is Register successfull\"  ");
            //return ResponseEntity.ok("\"User is Register successfull\"");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Not register");
        }
    }

    @GetMapping("/roleList")
    public List<Role> roleList(@RequestParam(required = false) Integer status) {
        try {

            if (status != null) {
                return roleRepo.findByStatus(status);
            } else {
                return roleRepo.findAll();
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }


//    @GetMapping("/users")
//    public ResponseEntity<?> getAllUser() {
//        try {
//
//
//            List<User> usersWithIdAndName = userService.getUsersWithIdAndName();
//            return ResponseEntity.status(HttpStatus.OK).body(usersWithIdAndName);
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//
//    }
}
