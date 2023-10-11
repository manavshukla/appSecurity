package com.security.dto;


import lombok.Data;

@Data
public class CreateLeaveTypeDto {

    private String leaveName;
    private Integer maxDaysAllowed;
}
