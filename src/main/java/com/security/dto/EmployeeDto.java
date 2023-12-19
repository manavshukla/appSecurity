package com.security.dto;


import com.security.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.util.List;

@Data
@NoArgsConstructor
public class EmployeeDto {
    @ReadOnlyProperty
    @Id
    private String employeeId;
    private String userId;
    private String name;
    private String emailAddress;
    private String phoneNo;
    private Long emergencyContactNo;
    private String workingWith;
    private String reportTo;
    private Float experience;
    private String joiningDate;
    private Float availableLeaves;
    private Long salary;
    private String nextAppraisalDate;
    private String dateOfBirth;
    private String bloodGroup;
    private String maritalStatus;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String interestedTechnologies;
    private Long bankAccountNo;
    private String ifscCode;
    private String role;
    private String designation;
    private String currentProjectName;
    private String emergencyContactPerson;
    private String createdBy;
    private Integer status;


    private List<SystemList> systems;
    private EmployeeFileDto employeeFileDto;

}
