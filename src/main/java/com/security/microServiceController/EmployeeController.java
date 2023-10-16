package com.security.microServiceController;

import com.security.dto.EmployeeDto;
import com.security.dto.EmployeeFileDto;
import com.security.dto.SystemList;
import com.security.microService.EmployeeService;
import com.security.model.CustomResponse;
import com.security.model.Role;
import com.security.model.User;
import com.security.repo.RoleRepo;
import com.security.repo.UserRepo;
import com.security.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/call")
@CrossOrigin("*")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    private final UserServiceImpl userService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, RoleRepo roleRepo, UserRepo userRepo, UserServiceImpl userService) {
        this.employeeService = employeeService;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@ModelAttribute EmployeeDto employeeDto,
                                            @RequestParam("photo") MultipartFile photo, @RequestParam("adharCardFront") MultipartFile adharCardFront,
                                            @RequestParam("adharCardBack") MultipartFile adharCardBack, @RequestParam("panCard") MultipartFile panCard,
                                            @RequestParam("std10") MultipartFile std10, @RequestParam("std12") MultipartFile std12,
                                            @RequestParam("offerLetter") MultipartFile offerLetter, @RequestParam("degree") MultipartFile degree,
                                            @RequestParam("experienceLetter") MultipartFile experienceLetter, HttpServletRequest request
            , @ModelAttribute SystemList systemList) throws IOException {
        try {

            EmployeeFileDto fileDto = new EmployeeFileDto();
            fileDto.setPhoto(photo);
            fileDto.setAdharCardFront(adharCardFront);
            fileDto.setAdharCardBack(adharCardBack);
            fileDto.setPanCard(panCard);
            fileDto.setDegree(degree);
            fileDto.setStd10(std10);
            fileDto.setStd12(std12);
            fileDto.setOfferLetter(offerLetter);
            fileDto.setExperienceLetter(experienceLetter);
            System.out.println(employeeDto.getEmployeeId());
            System.out.println(fileDto.getPhoto());
//            Role role = roleRepo.insert(new Role());
            User user = new User();
            user.setFirstName(employeeDto.getName());
            user.setEmail(employeeDto.getEmailAddress());
            user.setUsername(employeeDto.getName());
            user.setRoleId(employeeDto.getRole());
//            user.setRoles(employeeDto.getRole());
//                    userRepo.findById(employeeDto.getUserId());
            //Role role =roleRepo.findById()
//            if (role.getRoleId().equals(user.getRoles())) {
//            user.get().setId(user.get().getEmployeeId());
            //employeeService.reg(user);
            user.setRoles(Role.createWithId(employeeDto.getRole()));
            //}
            User user1 = userService.registerUser(user, "");
            employeeDto.setUserId(user1.getId());
            employeeService.createEmployee(employeeDto, request, fileDto, systemList);

            CustomResponse customResponse = new CustomResponse();
            customResponse.setMessage("\"Employee Created\"");
            customResponse.setUser(user1);


            return ResponseEntity.ok().body(customResponse);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body("Failed to create");
        }
    }

    @GetMapping("/employee-data")
    public ResponseEntity<?> getEmployeeData(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int pageSize) {
        try {

            String employeeData = employeeService.getEmployeeData(page, pageSize);
            return ResponseEntity.ok().body(employeeData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to load");
        }
    }

    @PutMapping("/employee-update/{id}")
    public ResponseEntity<?> updateEmployee(@ModelAttribute EmployeeDto employeeDto, @PathVariable String id, @ModelAttribute SystemList systemList, @RequestParam(required = false, name = "photo") MultipartFile photo, @RequestParam(required = false, name = "adharCartFront") MultipartFile adharCartFront, @RequestParam(required = false, name = "adharCartBack") MultipartFile adharCartBack, @RequestParam(required = false, name = "panCard") MultipartFile panCard, @RequestParam(required = false, name = "std10") MultipartFile std10, @RequestParam(required = false, name = "std12") MultipartFile std12, @RequestParam(required = false, name = "offerLetter") MultipartFile offerLetter, @RequestParam(required = false, name = "degree") MultipartFile degree, @RequestParam(required = false, name = "exprienceLetter") MultipartFile exprienceLetter) throws IOException {
        try {

            EmployeeFileDto fileDto = new EmployeeFileDto();
            fileDto.setPhoto(photo);
            fileDto.setAdharCardFront(adharCartFront);
            fileDto.setAdharCardBack(adharCartBack);
            fileDto.setPanCard(panCard);
            fileDto.setDegree(degree);
            fileDto.setStd10(std10);
            fileDto.setStd12(std12);
            fileDto.setOfferLetter(offerLetter);
            fileDto.setExperienceLetter(exprienceLetter);


//            if (photo == null) {
//                fileDto.setPhoto(fileDto.getPhoto());
//            } else {
//                fileDto.setPhoto(photo);
//            }
//            if (adharCartBack == null) {
//                fileDto.setAdharCartBack(fileDto.getAdharCartBack());
//            } else {
//                fileDto.setAdharCartBack(adharCartBack);
//            }
//            if (adharCartFront == null) {
//                fileDto.setAdharCartFront(fileDto.getAdharCartFront());
//            } else {
//                fileDto.setAdharCartFront(adharCartFront);
//            }
//
//            if (degree == null) {
//                fileDto.setDegree(fileDto.getDegree());
//            } else {
//                fileDto.setDegree(degree);
//            }
//            if (exprienceLetter == null) {
//                fileDto.setExprienceLetter(fileDto.getExprienceLetter());
//            } else {
//                fileDto.setExprienceLetter(exprienceLetter);
//            }
//            if (offerLetter == null) {
//                fileDto.setOfferLetter(fileDto.getOfferLetter());
//            } else {
//                fileDto.setOfferLetter(offerLetter);
//            }
//            if (std10 == null) {
//                fileDto.setStd10(fileDto.getStd10());
//            } else {
//                fileDto.setStd10(std10);
//            }
//            if (std12 == null) {
//                fileDto.setStd12(fileDto.getStd12());
//            } else {
//                fileDto.setStd12(std12);
//            }
//            if (panCard == null) {
//                fileDto.setPanCard(fileDto.getPanCard());
//            } else {
//                fileDto.setPanCard(panCard);
//            }

            employeeService.updateEmployee(employeeDto, fileDto, id, systemList);
            return ResponseEntity.ok().body("\"Employee Update\"");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Employee not update");
        }
    }

    @DeleteMapping("/employee-delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        try {

            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().body("\"Employee Delete sucess\"");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete");
        }
    }

    @GetMapping("/employee-id/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id) {
        try {

            String timesheetByID = employeeService.getEmployeeById(id);
            if (timesheetByID != null) {
                return ResponseEntity.ok().body(timesheetByID);
            } else {
                return ResponseEntity.badRequest().body("\"Id not Found\"");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Not found");
        }
    }


    @GetMapping("/employee-name")
    public ResponseEntity<?> getEmployeeByName() {
        try {

            String employeeData = employeeService.getEmployeeByName();
            return ResponseEntity.ok().body(employeeData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to load");
        }
    }


    @GetMapping("/employee-user/{id}")
    public ResponseEntity<?> getEmployeeData(@PathVariable String id) {
        try {

            EmployeeDto employeeData = employeeService.getEmployeeByUserId(id);
            return ResponseEntity.ok().body(employeeData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to load");
        }
    }


}
