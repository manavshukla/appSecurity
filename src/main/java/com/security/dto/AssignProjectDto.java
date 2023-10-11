package com.security.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class AssignProjectDto {
    private String empId;
    private String projectId;
    private LocalDate startdate;
    private LocalDate enddate;
    private Date createdate;
    private Integer status;
}
