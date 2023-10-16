package com.security.service;

import com.security.dto.EmployeeDto;
import com.security.microService.EmployeeService;
import com.security.model.JwtToken;
import com.security.model.JwtTokenProvider;
import com.security.model.Role;
import com.security.model.User;
import com.security.repo.JwtTokenRepo;
import com.security.repo.RoleRepo;
import com.security.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.jwt.secret}")
    private String secretKey;
    private final UserRepo userRepo;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepo roleRepo;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenRepo jwtTokenRepo;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, JwtTokenRepo jwtTokenRepo, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RoleRepo roleRepo, AuthenticationManager authenticationManager, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, JwtTokenRepo jwtTokenRepo1, RestTemplate restTemplate, EmployeeService employeeService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepo = roleRepo;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenRepo = jwtTokenRepo1;
        this.employeeService = employeeService;
    }

    private List<EmployeeDto> employees = new ArrayList<>();

    public void addEmployee(EmployeeDto employee) {
        employees.add(employee);
    }

    @Override
    public User registerUser(User user, String roleId) {
        try {
            String id = user.getRoleId();
            Optional<Role> role = roleRepo.findById(id);
            user.setRoles(role.get());
            String encodedPassword = "";
            if (Objects.equals(roleId, "")) {
                int passwordLength = 12; // You can adjust the password length as needed
                // encodedPassword = generatePassword(passwordLength);
                //This is the simple string PASSWORD CREATED THAT should be sent to the user :Hely
                String randomPassword = generateRandomString(); // Generate a random password
                encodedPassword = passwordEncoder.encode(randomPassword);
                user.setPassword(encodedPassword);

                User save = userRepo.save(user);

                System.out.println(randomPassword);
                System.out.println("en" + encodedPassword);
                save.setPassword(randomPassword);

            } else {
                //user.setRoles(role.get());
                encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setStatus(1);
                User save = userRepo.save(user);
            }

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return user;
    }

    //    public String generatePassword(int length) {
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
//        SecureRandom random = new SecureRandom();
//        StringBuilder password = new StringBuilder();
//
//        for (int i = 0; i < length; i++) {
//            int randomIndex = random.nextInt(characters.length());
//            password.append(characters.charAt(randomIndex));
//        }
//
//        return passwordEncoder.encode(password);
//    }
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int RANDOM_STRING_LENGTH = 10;

    public static String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public String login(String username, String password) {
        try {

            int status = 1;

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtTokenProvider.generateToken(userDetails, secretKey);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {

            return userRepo.findByUsername(username);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void logout(String token) {
        try {

            JwtToken token1 = jwtTokenRepo.findByToken(token);
            if (token1 != null) {
                jwtTokenRepo.delete(token1);

            } else {
                throw new RuntimeException("Invalid token");
            }
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public EmployeeDto getEmployeeByUserIdAndStatus(String userId, int status) {
        for (EmployeeDto employeeDto : employees) {
            if (employeeDto.getUserId().equals(userId) && employeeDto.getStatus() == status) {
                return employeeDto;
            }
        }
        return null;
    }


}
